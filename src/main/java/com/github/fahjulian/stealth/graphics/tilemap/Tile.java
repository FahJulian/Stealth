package com.github.fahjulian.stealth.graphics.tilemap;

import com.github.fahjulian.stealth.graphics.Sprite;

public class Tile
{
    private final Sprite sprite;

    public Tile(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public Sprite getSprite()
    {
        return sprite;
    }
}
