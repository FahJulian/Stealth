package com.github.fahjulian.stealth.tilemap;

import com.github.fahjulian.stealth.graphics.opengl.AbstractDynamicModel;

/** The model for the 2D tile map. */
public class TileMapModel extends AbstractDynamicModel
{
    private final TileMap map;

    TileMapModel(TileMap map)
    {
        super(map.getWidth() * map.getHeight() * 4, 3, 2, 1);

        this.map = map;

        for (int x = 0; x < map.getWidth(); x++)
            for (int y = 0; y < map.getHeight(); y++)
                setTile(x, y, map.getTiles()[x + y * map.getWidth()]);
        super.rebuffer();
    }

    void setTile(int x, int y, Tile tile)
    {
        float[] textureCoords = tile.getSprite().getTextureCoords();
        float textureSlot = map.getTextures().indexOf(tile.getSprite().getTexture());

        for (int i = 0; i < 4; i++)
        {
            int idx = (x + y * map.getWidth()) * 4 + i;
            float posX = (x + 1 - i % 2) * map.getTileSize(), posY = (y + 1 - i / 2) * map.getTileSize();

            super.setVertex(idx, //
                    posX, posY, map.getPosZ(), //
                    textureCoords[i * 2 + 0], textureCoords[i * 2 + 1], //
                    textureSlot //
            );
        }
    }

    @Override
    public void rebuffer()
    {
        super.rebuffer();
    }

    @Override
    protected int[] generateIndices(int vertexCount)
    {
        int[] indices = new int[vertexCount * 6 / 4];
        for (int i = 0; i < vertexCount / 4; i++)
        {
            indices[0 + i * 6] = 2 + i * 4;
            indices[1 + i * 6] = 0 + i * 4;
            indices[2 + i * 6] = 1 + i * 4;
            indices[3 + i * 6] = 3 + i * 4;
            indices[4 + i * 6] = 2 + i * 4;
            indices[5 + i * 6] = 1 + i * 4;
        }

        return indices;
    }
}
