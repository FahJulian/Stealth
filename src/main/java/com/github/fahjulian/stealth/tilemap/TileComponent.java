package com.github.fahjulian.stealth.tilemap;

import java.util.Map;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.IComponentBlueprint;
import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.graphics.Sprite;

public class TileComponent extends AbstractComponent
{
    private Sprite sprite;
    private TileMap map;
    private int x, y; // coords on map

    public TileComponent(Sprite sprite)
    {
        this.sprite = sprite;
    }

    @Override
    protected void onInit()
    {
    }

    TileComponent init(TileMap map, int x, int y)
    {
        this.map = map;
        this.x = x;
        this.y = y;
        return this;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        if (map.canTileSwitchTexture(this, sprite))
        {
            this.sprite = sprite;
            map.getModel().setTile(x, y, this);
            map.getModel().rebuffer();
        }
    }

    public static final class Blueprint implements IComponentBlueprint<TileComponent>
    {
        private final Sprite sprite;

        public Blueprint(Sprite sprite)
        {
            this.sprite = sprite;
        }

        @Override
        public String getUniqueKey()
        {
            return sprite.getUniqueKey();
        }

        @Override
        public void serialize(Map<String, Object> fields)
        {
            fields.put("sprite", sprite);
        }

        @Override
        public TileComponent createComponent()
        {
            return new TileComponent(sprite);
        }

        @Deserializer
        public static Blueprint deserialize(Map<String, String> fields)
        {
            return new Blueprint(SerializablePool.<Sprite>deserialize(fields.get("sprite")));
        }
    }
}
