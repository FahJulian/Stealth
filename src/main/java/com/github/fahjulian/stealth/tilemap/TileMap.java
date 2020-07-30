package com.github.fahjulian.stealth.tilemap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.ResourcePool;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.renderer.IDrawable;

/** A 2D map that is made up from tiles. */
public class TileMap implements IResource, IDrawable
{
    private String filePath;
    private int width, height; // in tiles
    private float tileSize;
    private float posZ;
    private List<AbstractTexture> textures;
    private boolean loaded;

    private List<Entity> tiles;

    private Shader shader;
    private TileMapModel model;

    private final boolean loadFromFile;

    /**
     * Construct a new tile map object
     * 
     * @param filePath
     *                     The .xml file to load the map from
     */
    private TileMap(String filePath)
    {
        this.filePath = filePath;
        this.loadFromFile = true;
    }

    private TileMap(String filePath, int width, int height, float tileSize, float posZ, List<AbstractTexture> textures,
            Sprite[] tiles)
    {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.textures = textures;
        this.loadFromFile = false;

        this.tiles = new ArrayList<>();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                this.tiles.add(new Entity(String.format("TileMap %s tile (%d, %d)", this, x, y),
                        new Transform(x * tileSize, y * tileSize, posZ, tileSize, tileSize),
                        new TileComponent(tiles[x + y * width], this)));
            }
        }
    }

    @Override
    public void load() throws Exception
    {
        if (loadFromFile)
        {
            FileHandler.MapInfo mapInfo = FileHandler.loadMapInfo(filePath);
            this.width = mapInfo.width;
            this.height = mapInfo.height;
            this.tileSize = mapInfo.tileSize;
            this.posZ = mapInfo.posZ;
            this.textures = mapInfo.textures;

            this.tiles = new ArrayList<>();
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    tiles.add(new Entity(String.format("TileMap %s tile (%d, %d)", this, x, y),
                            new Transform(x * tileSize, y * tileSize, posZ, tileSize, tileSize),
                            new TileComponent(mapInfo.tiles[x + y * width], this)));
                }
            }
        }

        assert tiles.size() == width * height : Log.error("(TileMap) Map size does not match the amount of tiles.");
        assert textures.size() <= 16 : Log.error("(TileMap) A maximum of 16 textures is allowed.");

        this.model = new TileMapModel(this);
        shader = ResourcePool.getOrLoadResource(
                new Shader("/home/julian/dev/java/Stealth/src/main/resources/shaders/batched_textured_rectangle.glsl"));

        this.loaded = true;
    }

    /** Saves the map to its specified .xml file. */
    public void save()
    {
        try
        {
            Log.info("(TileMap) Saving map %s", filePath);
            FileHandler.saveMap(width, height, tileSize, posZ, new HashSet<>(textures), getTiles(), filePath);
        }
        catch (Exception e)
        {
            Log.error("(TileMap) Error saving map: %s", e.getMessage());
        }
    }

    @Override
    public void draw(Camera camera)
    {
        shader.bind();
        shader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        shader.setUniform("uViewMatrix", camera.getViewMatrix());
        shader.setUniform("uTextures", new int[] {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16
        });

        for (int i = 0; i < textures.size(); i++)
            textures.get(i).bind(i);

        model.draw();

        for (int i = 0; i < textures.size(); i++)
            textures.get(i).unbind(i);

        shader.unbind();
    }

    public TileComponent getTile(int x, int y)
    {
        return tiles.get(x + y * width).getComponent(TileComponent.class);
    }

    @Override
    public String getKey()
    {
        return filePath;
    }

    public List<Entity> getEntities()
    {
        return tiles;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public float getPosZ()
    {
        return posZ;
    }

    public float getTileSize()
    {
        return tileSize;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public TileMapModel getModel()
    {
        return model;
    }

    boolean canTileSwitchTexture(TileComponent t, Sprite newSprite)
    {
        AbstractTexture texture = newSprite.getTexture();
        if (!textures.contains(texture))
        {
            if (textures.size() >= 16)
            {
                textures.clear();
                tiles.forEach((e) ->
                {
                    TileComponent tile = e.getComponent(TileComponent.class);
                    if (!textures.contains(tile.getSprite().getTexture()) && !(tile == t))
                        textures.add(tile.getSprite().getTexture());
                });

                if (textures.size() >= 16)
                    return false;
            }
        }

        textures.add(texture);
        return true;
    }

    void updateModel()
    {
        if (loaded)
            model.rebuffer();
    }

    TileComponent[] getTiles()
    {
        final List<TileComponent> tiles = new ArrayList<>();
        this.tiles.forEach((e) -> tiles.add(e.getComponent(TileComponent.class)));
        return tiles.toArray(new TileComponent[width * height]);
    }

    List<AbstractTexture> getTextures()
    {
        return textures;
    }

    /**
     * Create a new map from a know set of tiles
     * 
     * @param filePath
     *                     The file to later save the map to. Must be unique to all
     *                     tile maps
     * @param width
     *                     The width of the map (Amount of tiles per column)
     * @param height
     *                     The height of the map (Amount of tiles per row)
     * @param tileSize
     *                     The size of each tile in pixels
     * @param posZ
     *                     The z-Position of the map in the world
     * @param tiles
     *                     Array of all the tiles. Must fit the size of the map
     * 
     * @return The newly created Tile Map object. Must be loaded with the Resource
     *         Pool.
     */
    public static TileMap create(String filePath, int width, int height, float tileSize, float posZ, Sprite[] tiles)
    {
        final List<AbstractTexture> textures = new ArrayList<>();
        for (Sprite tile : tiles)
            if (!textures.contains(tile.getTexture()))
                textures.add(tile.getTexture());

        return new TileMap(filePath, width, height, tileSize, posZ, textures, tiles);
    }

    /**
     * Create a map that is going to be loaded from a .xml file
     * 
     * @param filePath
     *                     The full path to the file
     * 
     * @return The tilemap object. Must be loaded with the Resource Pool.
     */
    public static TileMap fromFile(String filePath)
    {
        return new TileMap(filePath);
    }
}
