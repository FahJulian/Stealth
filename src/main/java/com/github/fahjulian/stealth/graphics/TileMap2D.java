package com.github.fahjulian.stealth.graphics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class TileMap2D
{
    public static class TileSet
    {
        private String name;
        private float tileSize;
        private int width, height;
        private Texture2D[] textures;
        private int[] textureIndices;

        public TileSet(String name, float tileSize, int width, int height, Texture2D[] textures, int[] textureIndices)
        {
            if (!(textureIndices.length == width * height))
            {
                Log.error("(TileSet) Must provide an array of Texture IDs that has one ID for each Tile.");
                return;
            }
            if (textures.length >= 17)
            {
                Log.error("(TileSet) The maximum amount of Textures allowed for a TileSet is 16.");
                return;
            }

            this.name = name.replaceAll(" ", "");
            this.tileSize = tileSize;
            this.width = width;
            this.height = height;
            this.textures = textures;
            this.textureIndices = textureIndices;
        }

        public void saveToFile(String folder)
        {
            StringBuilder text = new StringBuilder();

            for (Texture2D texture : textures)
                text.append(String.format("<texture>%s</texture>%n", texture.toString()));

            text.append(String.format("<map>%n"));
            for (int y = 0; y < height; y++)
            {
                text.append(String.format("    <row>%n"));
                for (int x = 0; x < width; x++)
                {
                    text.append(String.format("        <col>%d</col>%n", textureIndices[x + y * width]));
                }
                text.append(String.format("    </row>%n"));
            }
            text.append(String.format("</map>%n"));

            try
            {
                new File(folder).mkdirs();
                FileWriter writer = new FileWriter(new File(folder + name + ".stealthTileSet.xml"));
                writer.write(text.toString());
                writer.close();
                Log.info("(TileSet) Saved map to file.");
            }
            catch (IOException ex)
            {
                Log.error("(TileSet) Could not save map to file.");
            }
        }
    }

    private final TexturedModel model;
    private final Texture2D[] textures;

    public TileMap2D(TileSet tileSet, float posZ)
    {
        float[] positions = calculatePositions(tileSet.tileSize, tileSet.width, tileSet.height, posZ);
        float[] textureCoords = calculateTextureCoords(tileSet.width, tileSet.height);
        float[] textureSlots = calculateTextureSlots(tileSet.width, tileSet.height, tileSet.textureIndices);
        int[] indices = calculateIndices(tileSet.width, tileSet.height);

        this.model = new TexturedModel(positions, textureCoords, textureSlots, indices);
        this.textures = tileSet.textures;
    }

    void bindTextures()
    {
        for (int i = 0; i < textures.length && textures[i] != null; i++)
            textures[i].bind(i);
    }

    void unbindTextures()
    {
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
                    positions[(x + y * width) * 4 * 3 + i * 3 + 0] = tileSize * (x + 1 - i % 2);
                    positions[(x + y * width) * 4 * 3 + i * 3 + 1] = tileSize * (y + 1 - i / 2);
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
                    textureCoords[(x + y * width) * 4 * 2 + i * 2 + 0] = 1 - (i % 2);
                    textureCoords[(x + y * width) * 4 * 2 + i * 2 + 1] = 1 - (i / 2);
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
                        textureSlots[(x + y * width) * 4 * 1 + i * 1 + 0] = tileTextureSlots[x + y * width];
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
            indices[0 + i * 6] = 2 + i * 4;
            indices[1 + i * 6] = 0 + i * 4;
            indices[2 + i * 6] = 1 + i * 4;
            indices[3 + i * 6] = 3 + i * 4;
            indices[4 + i * 6] = 2 + i * 4;
            indices[5 + i * 6] = 1 + i * 4;
        }

        return indices;
    }

    TexturedModel getModel()
    {
        return model;
    }
}
