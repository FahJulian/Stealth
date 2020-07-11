package com.github.fahjulian.stealth.graphics.tilemap;

import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class MapTile
{
    private final Texture2D texture;

    public MapTile(Texture2D texture)
    {
        this.texture = texture;
    }

    public Texture2D getTexture()
    {
        return texture;
    }
}
