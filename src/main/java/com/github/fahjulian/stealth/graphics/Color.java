package com.github.fahjulian.stealth.graphics;

import org.joml.Vector4f;

public class Color extends Vector4f
{
    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f), LIGHT_GREY = new Color(0.6f, 0.6f, 0.6f, 1.0f);

    public Color(float r, float g, float b, float a)
    {
        super(r, g, b, a);
    }
}
