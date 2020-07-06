package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;
import com.github.fahjulian.stealth.graphics.opengl.VertexBuffer;

public class TileMapModel
{
    public static class MapData
    {
        int width, height;
        float tileSize;
        float posZ;
        Texture2D[] textures;
        int[] textureIndices;

        MapData()
        {
        }

        MapData(int width, int height, float tileSize, float posZ, Texture2D[] textures, int[] textureIndices)
        {
            this.width = width;
            this.height = height;
            this.tileSize = tileSize;
            this.posZ = posZ;
            this.textures = textures;
            this.textureIndices = textureIndices;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }

        public float getTileSize()
        {
            return tileSize;
        }

        public float getPosZ()
        {
            return posZ;
        }

        public int[] getTextureIndices()
        {
            return textureIndices;
        }

        public Texture2D[] getTextures()
        {
            return textures;
        }
    }

    private final VertexArray vao;
    private final int vertexCount;
    private final Texture2D[] textures;

    TileMapModel(MapData data)
    {
        float[] positions = calculatePositions(data.tileSize, data.width, data.height, data.posZ);
        float[] textureCoords = calculateTextureCoords(data.width, data.height);
        float[] textureSlots = calculateTextureSlots(data.width, data.height, data.textureIndices);
        int[] indices = calculateIndices(data.width, data.height);

        this.vao = new VertexArray();
        this.vertexCount = indices.length;

        VertexBuffer positionsVBO = new VertexBuffer(positions, 3);
        vao.addVBO(positionsVBO);
        positionsVBO.unbind();

        VertexBuffer textureCoordsVBO = new VertexBuffer(textureCoords, 2);
        vao.addVBO(textureCoordsVBO);
        textureCoordsVBO.unbind();

        VertexBuffer textureSlotsVBO = new VertexBuffer(textureSlots, 1);
        vao.addVBO(textureSlotsVBO);
        textureCoordsVBO.unbind();

        ElementBuffer ebo = new ElementBuffer(indices);
        ebo.unbind();

        vao.unbind();

        this.textures = new Texture2D[16];
        for (int i = 0; i < data.textures.length; i++)
            this.textures[i] = data.textures[i];
    }

    void bind()
    {
        vao.bind();

        for (int i = 0; i < textures.length && textures[i] != null; i++)
            textures[i].bind(i);
    }

    void unbind()
    {
        vao.unbind();

        for (int i = 0; i < textures.length && textures[i] != null; i++)
            textures[i].unbind(i);
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

    private float[] calculateTextureSlots(int width, int height, int[] tileTextureSlots)
    {
        float[] textureSlots = new float[width * height * 4 * 1];
        {
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        textureSlots[(x + y * width) * 4 * 1 + i * 1 + 0] = tileTextureSlots[tileTextureSlots.length - 1
                                - (x + y * width)]; // FIXME: This is bad
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

    int getVertexCount()
    {
        return vertexCount;
    }
}
