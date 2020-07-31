package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

/** A texture holding multiple sprites */
public class Spritesheet extends AbstractTexture implements IResource
{
    static
    {
        SerializablePool.register(Spritesheet.class, Spritesheet::deserialize);
    }

    private final List<Sprite> sprites;
    private final String filePath;
    private final int width, height; // Number of sprites
    private final int spriteWidth, spriteHeight;
    private final int padding;

    /**
     * Construct a new spritesheet object
     * 
     * @param filePath
     *                         The path to the image file of the spritesheet
     * @param width
     *                         The amount of sprites per column
     * @param height
     *                         THe amount of sprites per row
     * @param spriteWidth
     *                         The width of each sprite in pixels
     * @param spriteHeight
     *                         The height of each sprite in pixels
     * @param padding
     *                         The padding on the right and bottom of each sprite in
     *                         pixels
     */
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
        for (int y = height; y > 0; y--)
        {
            for (int x = 0; x < width; x++)
            {
                float x0 = x * (spriteWidth + padding) / (float) textureSize[0];
                float y1 = y * (spriteHeight + padding) / (float) textureSize[1];
                float x1 = x0 + spriteWidth / (float) textureSize[0];
                float y0 = y1 - spriteHeight / (float) textureSize[1];

                sprites.add(new Sprite(this, x0, x1, y0, y1));
            }
        }
    }

    /**
     * Get the sprite located at the given coordinates
     * 
     * @param x
     *              The column of the sprite
     * @param y
     *              The row of the sprite
     * @return The sprite at the given coordinates
     */
    public Sprite getSpriteAt(int x, int y)
    {
        return sprites.get(x + y * width);
    }

    /**
     * Retrieve the position of a given sprite on the spritesheet
     * 
     * @param sprite
     *                   The sprite to check the position of
     * 
     * @return An integer array holding the x-position (column) and y-position (row)
     *         of the sprite on the spritesheet.
     */
    public int[] posOf(Sprite sprite)
    {
        final int i = sprites.indexOf(sprite);
        return new int[] {
                i % width, //
                i / width
        };
    }

    /**
     * @return The amount of sprites per column
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @return The amount of sprites per row
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @return The width of each sprite in pixels
     */
    public int getSpriteWidth()
    {
        return spriteWidth;
    }

    /**
     * @return The height of each sprite in pixels
     */
    public int getSpriteHeight()
    {
        return spriteHeight;
    }

    /**
     * @return The padding on the left and the bottom of each sprite
     */
    public int getPadding()
    {
        return padding;
    }

    @Override
    public String getKey()
    {
        return filePath;
    }

    @Override
    public String getUniqueKey()
    {
        return filePath;
    }

    @Override
    public void serialize(Map<String, Object> fields)
    {
        fields.put("filePath", filePath);
        fields.put("width", width);
        fields.put("height", height);
        fields.put("spriteWidth", spriteWidth);
        fields.put("spriteHeight", spriteHeight);
        fields.put("padding", padding);
    }

    @Deserializer
    public static Spritesheet deserialize(Map<String, String> fields)
    {
        return new Spritesheet(fields.get("filePath"), Integer.valueOf(fields.get("width")),
                Integer.valueOf(fields.get("height")), Integer.valueOf(fields.get("spriteWidth")),
                Integer.valueOf(fields.get("spriteHeight")), Integer.valueOf(fields.get("padding")));
    }
}
