package com.github.fahjulian.stealth.graphics.tilemap;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.graphics.opengl.StaticVertexBuffer;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;
import com.github.fahjulian.stealth.graphics.renderer.AbstractModel;

public class TileMapModel extends AbstractModel
{
    private final Texture2D[] textures;

    public TileMapModel(MapData data)
    {
        float[] positions = calculatePositions(data.tileSize, data.width, data.height, data.posZ);
        float[] textureCoords = calculateTextureCoords(data.width, data.height);
        float[] textureSlots = calculateTextureSlots(data.width, data.height, data.textures, data.tiles);
        int[] indices = calculateIndices(data.width, data.height);

        new StaticVertexBuffer(positions, 3, vao);
        new StaticVertexBuffer(textureCoords, 2, vao);
        new StaticVertexBuffer(textureSlots, 1, vao);
        setIndicesBuffer(indices);

        this.textures = new Texture2D[16];
        for (int i = 0; i < data.textures.length; i++)
            this.textures[i] = data.textures[i];
    }

    @Override
    public void draw()
    {
        vao.bind();

        for (int i = 0; i < textures.length && textures[i] != null; i++)
            textures[i].bind(i);

        glDrawElements(GL_TRIANGLES, ebo.getSize(), GL_UNSIGNED_INT, 0);

        for (int i = 0; i < textures.length && textures[i] != null; i++)
            textures[i].unbind(i);

        vao.unbind();
    }

    private float[] calculatePositions(float tileSize, int width, int height, float posZ)
    {
        float[] positions = new float[width * height * 4 * 3];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int i = 0; i < 4; i++)
                {
                    positions[(x + y * width) * 4 * 3 + i * 3 + 0] = tileSize * (x + i % 2);
                    positions[(x + y * width) * 4 * 3 + i * 3 + 1] = tileSize * (y + i / 2);
                    positions[(x + y * width) * 4 * 3 + i * 3 + 2] = posZ;
                }
            }
        }

        return positions;
    }

    private float[] calculateTextureCoords(int width, int height)
    {
        float[] textureCoords = new float[width * height * 4 * 2];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                for (int i = 0; i < 4; i++)
                {
                    textureCoords[(x + y * width) * 4 * 2 + i * 2 + 0] = i % 2;
                    textureCoords[(x + y * width) * 4 * 2 + i * 2 + 1] = i / 2;
                }
            }
        }

        return textureCoords;
    }

    private float[] calculateTextureSlots(int width, int height, Texture2D[] textures, MapTile[] tiles)
    {
        float[] textureSlots = new float[width * height * 4 * 1];
        {
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        textureSlots[(x + y * width) * 4 * 1 + i * 1 + 0] = (float) Toolbox.indexOf(textures,
                                tiles[tiles.length - 1 - (x + y * width)].getTexture());
                    }
                }
            }
        }

        return textureSlots;
    }

    private int[] calculateIndices(int width, int height)
    {
        int[] indices = new int[width * height * 6];
        for (int i = 0; i < width * height; i++)
        {
            indices[0 + i * 6] = 1 + i * 4;
            indices[1 + i * 6] = 3 + i * 4;
            indices[2 + i * 6] = 2 + i * 4;
            indices[3 + i * 6] = 2 + i * 4;
            indices[4 + i * 6] = 0 + i * 4;
            indices[5 + i * 6] = 1 + i * 4;
        }

        return indices;
    }
}