package com.github.fahjulian.stealth.graphics.tilemap;

import java.util.List;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;
import com.github.fahjulian.stealth.graphics.renderer.BatchedTexturedModel;

public class TileMapModel extends BatchedTexturedModel
{
    private final float tileSize, posZ;
    private final List<AbstractTexture> textures;

    public TileMapModel(int width, int height, float tileSize, float posZ, List<AbstractTexture> textures, Tile[] tiles)
    {
        super(width * height);

        this.tileSize = tileSize;
        this.posZ = posZ;
        this.textures = textures;

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                setTile(x, y, tiles[x + y * width]);
        super.rebuffer();
    }

    public void setTile(int x, int y, Tile tile)
    {
        super.addRect(x * tileSize, y * tileSize, posZ, tileSize, tileSize, tile.getSprite().getTextureCoords(),
                textures.indexOf(tile.getSprite().getTexture()));
    }

    @Deprecated
    @Override
    public void addRect(float x, float y, float z, float width, float height, float[] textureIndices, int textureSlot)
    {
        Log.warn("TileMapModel.addRect() should not be called");
    }

    @Deprecated
    @Override
    public void clear()
    {
        Log.warn("TileMapModel.clear() should not be called.");
    }
}
