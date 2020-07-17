package com.github.fahjulian.stealth.graphics.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.ResourcePool;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.renderer.IDrawable;

public class TileMap implements IResource, IDrawable
{
    private final String filePath;
    private int width, height; // in tiles
    private float tileSize;
    private float posZ;
    private Tile[] tiles;
    private List<AbstractTexture> textures;

    private Shader shader;
    private TileMapModel model;

    public TileMap(String filePath)
    {
        this.filePath = filePath;
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
    }

    @Override
    public void load() throws Exception
    {
        if (textures == null && tiles == null) // not loaded yet
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

        model = new TileMapModel(width, height, tileSize, posZ, textures, tiles);
        shader = ResourcePool.getOrLoadResource(
                new Shader("/home/julian/dev/java/Stealth/src/main/resources/shaders/batched_textured_rectangle.glsl"));
    }

    public void save()
    {
        try
        {
            Log.info("(TileMap) Saving map %s", filePath);
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

    public void setTile(int x, int y, Tile tile)
    {
        if (x < 0 || y < 0 || x >= width || y >= height)
        {
            Log.warn("(TileMap) Error setting tile: (%d, %d) out of bounds.", x, y);
            return;
        }

        Tile oldTile = tiles[x + y * width];
        tiles[x + y * width] = tile;

        boolean canAddTexture = true;
        AbstractTexture t = tile.getSprite().getTexture();
        if (!textures.contains(t) && textures.size() >= 16)
        {
            updateTextures();
            canAddTexture = textures.size() < 16;
        }

        if (canAddTexture)
        {
            textures.add(t);
            model.setTile(x, y, tile);
            model.rebuffer();
        }
        else
        {
            tiles[x + y * width] = oldTile;
            Log.warn("(TileMap) Error setting tile: Can't add another texture.");
        }
    }

    private void updateTextures()
    {
        for (Tile tile : tiles)
        {
            AbstractTexture t = tile.getSprite().getTexture();
            if (!textures.contains(t))
                textures.add(t);
        }
    }

    public static TileMap create(String filePath, int width, int height, float tileSize, float posZ, Tile[] tiles)
    {
        final List<AbstractTexture> textures = new ArrayList<>();
        for (Tile tile : tiles)
            if (!textures.contains(tile.getSprite().getTexture()))
                textures.add(tile.getSprite().getTexture());

        return new TileMap(filePath, width, height, tileSize, posZ, textures, tiles);
    }

    @Override
    public String getKey()
    {
        return filePath;
    }
}
