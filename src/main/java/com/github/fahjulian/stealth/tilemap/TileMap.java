package com.github.fahjulian.stealth.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.ResourcePool;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.core.util.Log;
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
    private Tile[] tiles;
    private List<AbstractTexture> textures;

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
            Tile[] tiles)
    {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.textures = textures;
        this.tiles = tiles;
        this.loadFromFile = false;
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
            this.tiles = mapInfo.tiles;
        }

        assert tiles.length == width * height : Log.error("(TileMap) Map size does not match the amount of tiles.");
        assert textures.size() <= 16 : Log.error("(TileMap) A maximum of 16 textures is allowed.");

        this.model = new TileMapModel(this);
        shader = ResourcePool.getOrLoadResource(
                new Shader("/home/julian/dev/java/Stealth/src/main/resources/shaders/batched_textured_rectangle.glsl"));
    }

    /** Saves the map to its specified .xml file. */
    public void save()
    {
        try
        {
            Log.info("(TileMap) Saving map %s", filePath);
            updateTextures();
            FileHandler.saveMap(width, height, tileSize, posZ, textures, tiles, filePath);
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

    /**
     * Sets the tile at the given coordinates
     * 
     * @param x
     *                 The column of the tile
     * @param y
     *                 The row of the tile
     * @param tile
     *                 The new tile
     */
    public void setTile(int x, int y, Tile tile)
    {
        if (x < 0 || y < 0 || x >= width || y >= height)
        {
            Log.debug("(TileMap) Error setting tile: (%d, %d) out of bounds.", x, y);
            return;
        }

        Tile oldTile = tiles[x + y * width];
        tiles[x + y * width] = null;

        AbstractTexture t = tile.getSprite().getTexture();
        if (!textures.contains(t))
        {
            updateTextures();
            if (textures.size() >= 16)
            {
                Log.debug("(TileMap) Error setting tile: Can't add another texture.");
                tiles[x + y * width] = oldTile;
                return;
            }

            textures.add(t);
        }

        tiles[x + y * width] = tile;
        model.setTile(x, y, tile);
        model.rebuffer();
    }

    /**
     * @param x
     *              The column of the tile
     * @param y
     *              The row of the tile
     * 
     * @return The tile at the given coordinates or null if coordinates are out of
     *         bound.
     */
    public Tile getTile(int x, int y)
    {
        if (x < 0 || y < 0 || x >= width || y >= height)
        {
            Log.debug("(TileMap) Error getting map tile: (%d, %d) out of bounds.", x, y);
            return null;
        }

        return tiles[x + y * width];
    }

    /**
     * Iterates over all tiles to make sure only the necessary textures are in the
     * texture list
     */
    private void updateTextures()
    {
        textures.clear();
        for (Tile tile : tiles)
        {
            if (tile == null)
                continue;
            AbstractTexture t = tile.getSprite().getTexture();
            if (!textures.contains(t))
                textures.add(t);
        }
    }

    @Override
    public String getKey()
    {
        return filePath;
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

    Tile[] getTiles()
    {
        return tiles;
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
    public static TileMap create(String filePath, int width, int height, float tileSize, float posZ, Tile[] tiles)
    {
        final List<AbstractTexture> textures = new ArrayList<>();
        for (Tile tile : tiles)
            if (!textures.contains(tile.getSprite().getTexture()))
                textures.add(tile.getSprite().getTexture());

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
