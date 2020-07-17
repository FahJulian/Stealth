package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

/** A sprite is an objet that holds a texture and texture coords */
public class Sprite
{
    private AbstractTexture texture;
    private float[] textureCoords;

    /**
     * Constuct a sprite that uses the whole texture
     * 
     * @param texture
     *                    The texture the sprite is on
     */
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

    /**
     * Construct a sprite that uses only part of the texture
     * 
     * @param texture
     *                          The texture the sprite is on
     * @param textureCoords
     *                          An array of floats defining the area of the sprite
     *                          on the texture
     */
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
