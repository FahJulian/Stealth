package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

public class Sprite
{
    private AbstractTexture texture;
    private float[] textureCoords;

    public Sprite(AbstractTexture texture)
    {
        this.texture = texture;
        this.textureCoords = new float[] {
                1.0f, 1.0f, //
                0.0f, 1.0f, //
                1.0f, 0.0f, //
                0.0f, 0.0f
        };
    }

    public Sprite(AbstractTexture texture, float[] textureCoords)
    {
        this.texture = texture;
        this.textureCoords = textureCoords;
    }

    public AbstractTexture getTexture()
    {
        return texture;
    }

    public float[] getTextureCoords()
    {
        return textureCoords;
    }
}
