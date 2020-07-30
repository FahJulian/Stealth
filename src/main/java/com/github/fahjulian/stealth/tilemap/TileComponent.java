package com.github.fahjulian.stealth.tilemap;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.graphics.Sprite;

public class TileComponent extends AbstractComponent
{
    private final TileMap map;
    private Sprite sprite;

    public TileComponent(Sprite sprite, TileMap map)
    {
        this.map = map;
        this.sprite = sprite;
    }

    @Override
    protected void onInit()
    {
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        if (map.canTileSwitchTexture(this, sprite))
            this.sprite = sprite;

        map.updateModel();
    }
}
