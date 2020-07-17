package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

public class Spritesheet extends AbstractTexture implements IResource
{
    private final List<Sprite> sprites;
    private final String filePath;
    private final int width, height; // Number of sprites
    private final int spriteWidth, spriteHeight;
    private final int padding;

    public Spritesheet(String filePath, int width, int height, int spriteWidth, int spriteHeight, int padding)
    {
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.padding = padding;
        this.sprites = new ArrayList<>();
    }

    @Override
    public void load()
    {
        int[] textureSize = super.load(filePath);

        // Extract sprites
        for (int y = this.height; y > 0; y--)
        {
            for (int x = 0; x < this.width; x++)
            {
                float x0 = x * (spriteWidth + padding) / (float) textureSize[0];
                float y1 = y * (spriteHeight + padding) / (float) textureSize[1];
                float x1 = x0 + spriteWidth / (float) textureSize[0];
                float y0 = y1 - spriteHeight / (float) textureSize[1];

                float[] textureCoords = new float[] {
                        x1, y1, //
                        x0, y1, //
                        x1, y0, //
                        x0, y0
                };

                sprites.add(new Sprite(this, textureCoords));
            }
        }
    }

    public Sprite getSpriteAt(int x, int y)
    {
        return sprites.get(x + y * width);
    }

    public int[] posOf(Sprite sprite)
    {
        final int i = sprites.indexOf(sprite);
        return new int[] {
                i % width, //
                i / width
        };
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getSpriteWidth()
    {
        return spriteWidth;
    }

    public int getSpriteHeight()
    {
        return spriteHeight;
    }

    public int getPadding()
    {
        return padding;
    }

    @Override
    public String getKey()
    {
        return filePath;
    }
}
