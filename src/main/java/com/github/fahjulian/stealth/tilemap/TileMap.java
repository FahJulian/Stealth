package com.github.fahjulian.stealth.tilemap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.renderer.IDrawable;

/** A 2D map that is made up from tiles. */
public class TileMap implements IDrawable, ISerializable
{
    private final int width, height; // in tiles
    private final float tileSize;
    private final float posZ;
    private final List<AbstractTexture> textures;
    private final List<Entity> tiles;

    private String filePath;
    private Shader shader;
    private TileMapModel model;

    public TileMap(String filePath, int width, int height, float tileSize, float posZ, List<AbstractTexture> textures,
            Sprite[] tiles)
    {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.posZ = posZ;
        this.textures = textures;
        this.tiles = new ArrayList<>();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                this.tiles.add(new Entity(String.format("TileMap %s tile (%d, %d)", this, x, y),
                        new Transform(x * tileSize, y * tileSize, posZ, tileSize, tileSize),
                        new TileComponent(tiles[x + y * width]).init(this, x, y)));
            }
        }
    }

    @Override
    public void load()
    {
        // TODO: Add shader
        this.shader = SerializablePool.getLoaded(
                new Shader("/home/julian/dev/java/Stealth/src/main/resources/shaders/batched_textured_rectangle.glsl"));
        this.model = new TileMapModel(this);
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

    @Override
    public String getUniqueKey()
    {
        return filePath;
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

    @Override
    public void serialize(Map<String, Object> fields)
    {
        fields.put("filePath", filePath);
        fields.put("width", width);
        fields.put("height", height);
        fields.put("tileSize", tileSize);
        fields.put("posZ", posZ);

        for (int i = 0; i < textures.size(); i++)
            fields.put(String.format("sheet%d", i), textures.get(i));

        final Set<Sprite> spritesSet = new HashSet<>();
        tiles.forEach(t -> spritesSet.add(t.getComponent(TileComponent.class).getSprite()));

        List<Sprite> sprites = new ArrayList<>(spritesSet);
        for (int i = 0; i < sprites.size(); i++)
            fields.put(String.format("sprite%d", i), sprites.get(i));

        for (int i = 0; i < tiles.size(); i++)
            fields.put(String.format("tile%d", i),
                    sprites.indexOf(tiles.get(i).getComponent(TileComponent.class).getSprite()));
    }

    @Deserializer
    public static TileMap deserialize(Map<String, String> fields)
    {
        String filePath = fields.get("filePath");
        int width = Integer.valueOf(fields.get("width"));
        int height = Integer.valueOf(fields.get("height"));
        float tileSize = Float.valueOf(fields.get("tileSize"));
        float posZ = Float.valueOf(fields.get("posZ"));

        String s = null;
        List<AbstractTexture> textures = new ArrayList<>();
        for (int i = 0; (s = fields.get(String.format("sheet%d", i))) != null; i++)
            textures.add(SerializablePool.<AbstractTexture>deserialize(s));
        List<Sprite> spriteOptions = new ArrayList<>();
        for (int i = 0; (s = fields.get(String.format("sprite%d", i))) != null; i++)
            spriteOptions.add(SerializablePool.<Sprite>deserialize(s));

        Sprite[] sprites = new Sprite[width * height];
        for (int i = 0; i < sprites.length; i++)
            sprites[i] = spriteOptions.get(Integer.valueOf(fields.get(String.format("tile%d", i))));

        return new TileMap(filePath, width, height, tileSize, posZ, textures, sprites);
    }
}
