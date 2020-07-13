package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Spritesheet extends Texture2D
{
    private final List<Sprite> sprites;
    private final int width, height; // Number of sprites
    private final int spriteWidth, spriteHeight;
    private final int padding;

    public Spritesheet(String filePath, int width, int height, int spriteWidth, int spriteHeight, int padding)
    {
        super(filePath);
        assert super.loadedSuccesfully() : Log
                .error("(Spritesheet) Error constructing Spritesheet: Texture did not load succesfully.");

        this.width = width;
        this.height = height;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.padding = padding;
        this.sprites = new ArrayList<>();

        // Extract sprites
        for (int y = this.height; y > 0; y--)
        {
            for (int x = 0; x < this.width; x++)
            {
                float x0 = x * (spriteWidth + padding) / (float) super.getWidth();
                float y1 = y * (spriteHeight + padding) / (float) super.getHeight();
                float x1 = x0 + spriteWidth / (float) super.getWidth();
                float y0 = y1 - spriteHeight / (float) super.getHeight();

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

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
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
}
