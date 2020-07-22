package com.github.fahjulian.stealth.tilemap;

import com.github.fahjulian.stealth.graphics.Sprite;

/**
 * Small class that holds information on map tiles. For now a map tile only has
 * a sprite
 */
public class Tile
{
    private Sprite sprite;

    public Tile(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }
}
