package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

/** A texture that uses the whole image it is loaded from */
public class Texture extends AbstractTexture implements IResource
{
    private final String filePath;
    private int width, height;

    /**
     * Construct a new texture
     * 
     * @param filePath
     *                     Path to the image file to load the texture from.
     */
    public Texture(String filePath)
    {
        this.filePath = filePath;
        this.width = this.height = -1;
    }

    @Override
    public void load()
    {
        int[] size = super.load(filePath);
        this.width = size[0];
        this.height = size[1];
    }

    @Override
    public String getKey()
    {
        return filePath;
    }

    /**
     * @return The width of the texture in pixels
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @return The height of the texture in pixels
     */
    public int getHeight()
    {
        return height;
    }

    public String getFilePath()
    {
        return filePath;
    }
}
