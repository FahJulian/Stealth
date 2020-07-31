package com.github.fahjulian.stealth.graphics;

import java.util.Map;

import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;

/** A sprite is an objet that holds a texture and texture coords */
public class Sprite implements IMaterial, ISerializable
{
    private final AbstractTexture texture;
    private final float[] textureCoords;

    /**
     * Constuct a sprite that uses the whole texture
     * 
     * @param texture
     *                    The texture the sprite is on
     */
    public Sprite(AbstractTexture texture)
    {
        this(texture, 0.0f, 1.0f, 0.0f, 1.0f);
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
    public Sprite(AbstractTexture texture, float x0, float x1, float y0, float y1)
    {
        this.texture = texture;
        this.textureCoords = new float[] {
                x1, y0, //
                x0, y0, //
                x1, y1, //
                x0, y1
        };
    }

    public AbstractTexture getTexture()
    {
        return texture;
    }

    public float[] getTextureCoords()
    {
        return textureCoords;
    }

    @Override
    public String getUniqueKey()
    {
        return String.format("%s@[%f, %f, %f, %f]", texture.getUniqueKey(), textureCoords[2], textureCoords[0],
                textureCoords[1], textureCoords[5]);
    }

    @Override
    public void serialize(Map<String, Object> fields)
    {
        fields.put("texture", texture);
        fields.put("x0", textureCoords[2]);
        fields.put("x1", textureCoords[0]);
        fields.put("y0", textureCoords[1]);
        fields.put("y1", textureCoords[5]);
    }

    @Deserializer
    public static Sprite deserialize(Map<String, String> fields)
    {
        return new Sprite(SerializablePool.<AbstractTexture>deserialize(fields.get("texture")),
                Float.valueOf(fields.get("x0")), Float.valueOf(fields.get("x1")), Float.valueOf(fields.get("y0")),
                Float.valueOf(fields.get("y1")));
    }
}
