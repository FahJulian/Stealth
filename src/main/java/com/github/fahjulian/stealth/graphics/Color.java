package com.github.fahjulian.stealth.graphics;

import org.joml.Vector4f;

/** Wrapper around the goml Vector4f representating a color */
public class Color extends Vector4f implements IMaterial
{
    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color LIGHT_GREY = new Color(0.6f, 0.6f, 0.6f, 1.0f);
    public static final Color DARK_GREY = new Color(0.2f, 0.2f, 0.2f, 1.0f);
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color GREEN = new Color(0.2f, 0.8f, 0.2f, 1.0f);
    public static final Color DARK_GREEN = new Color(0.1f, 0.6f, 0.1f, 1.0f);
    public static final Color RED = new Color(0.8f, 0.2f, 0.2f, 1.0f);
    public static final Color DARK_BLUE = new Color(0.2f, 0.2f, 0.8f, 1.0f);
    public static final Color LIGHT_BLUE = new Color(0.3f, 0.3f, 1.0f, 1.0f);

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
}
