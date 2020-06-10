package com.github.fahjulian.stealth.graphics;

import org.joml.Vector4f;

public class Color extends Vector4f
{
    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f), //
            LIGHT_GREY = new Color(0.6f, 0.6f, 0.6f, 1.0f), //
            DARK_GREY = new Color(0.2f, 0.2f, 0.2f, 1.0f);

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
        return super.z;
    }
}
