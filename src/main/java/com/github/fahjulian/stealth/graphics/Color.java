package com.github.fahjulian.stealth.graphics;

import java.util.Map;

import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.resources.SerializablePool;

import org.joml.Vector4f;

/** Wrapper around the goml Vector4f representating a color */
public class Color extends Vector4f implements IMaterial, ISerializable
{
    public static final Color WHITE, BLACK, LIGHT_GREY, DARK_GREY, LIGHT_RED, DARK_RED, LIGHT_GREEN, DARK_GREEN,
            LIGHT_BLUE, DARK_BLUE;

    static
    {
        WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        LIGHT_GREY = new Color(0.6f, 0.6f, 0.6f, 1.0f);
        DARK_GREY = new Color(0.2f, 0.2f, 0.2f, 1.0f);
        LIGHT_RED = new Color(0.6f, 0.1f, 0.1f, 1.0f);
        DARK_RED = new Color(0.8f, 0.2f, 0.2f, 1.0f);
        LIGHT_GREEN = new Color(0.2f, 0.8f, 0.2f, 1.0f);
        DARK_GREEN = new Color(0.1f, 0.6f, 0.1f, 1.0f);
        LIGHT_BLUE = new Color(0.3f, 0.3f, 1.0f, 1.0f);
        DARK_BLUE = new Color(0.2f, 0.2f, 0.8f, 1.0f);

        SerializablePool.register(Color.class, Color::deserialize);
    }

    public Color(float r, float g, float b, float a)
    {
        super(r, g, b, a);
    }

    public float getR()
    {
        return super.x;
    }

    public float getG()
    {
        return super.y;
    }

    public float getB()
    {
        return super.z;
    }

    public float getA()
    {
        return super.w;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Color))
            return false;

        Color c = (Color) obj;
        return super.x == c.x && super.y == c.y && super.z == c.z && super.w == c.w;
    }

    @Override
    public void serialize(Map<String, Object> fields)
    {
        fields.put("r", super.x);
        fields.put("g", super.y);
        fields.put("b", super.z);
        fields.put("a", super.w);
    }

    @Override
    public String getUniqueKey()
    {
        return String.format("%d%d%d", (int) (255 * super.x), (int) (255 * super.y), (int) (255 * super.z),
                (int) (255 * super.w));
    }

    @Deserializer
    public static Color deserialize(Map<String, String> fields)
    {
        float r = Float.valueOf(fields.get("r")), g = Float.valueOf(fields.get("g")),
                b = Float.valueOf(fields.get("b")), a = Float.valueOf(fields.get("a"));
        return new Color(r, g, b, a);
    }
}
