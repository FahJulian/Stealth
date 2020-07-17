package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.core.resources.IResource;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

public class PlainTexture extends AbstractTexture implements IResource
{
    private final String filePath;
    private int width, height;

    public PlainTexture(String filePath)
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

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
