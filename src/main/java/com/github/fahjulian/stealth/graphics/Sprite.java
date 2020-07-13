package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Sprite
{
    private Texture2D texture;
    private float[] textureCoords;

    public Sprite(Texture2D texture)
    {
        this.texture = texture;
        this.textureCoords = new float[] {
                1.0f, 1.0f, //
                0.0f, 1.0f, //
                1.0f, 0.0f, //
                0.0f, 0.0f
        };
    }

    public Sprite(Texture2D texture, float[] textureCoords)
    {
        this.texture = texture;
        this.textureCoords = textureCoords;
    }

    public Texture2D getTexture()
    {
        return texture;
    }

    public float[] getTextureCoords()
    {
        return textureCoords;
    }
}
