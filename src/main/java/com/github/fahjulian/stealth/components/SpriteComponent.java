package com.github.fahjulian.stealth.components;

import java.util.Map;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.IComponentBlueprint;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.event.EventListener;
import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

/**
 * Renders a given sprite at the entities position
 */
public class SpriteComponent extends AbstractComponent
{
    private final Sprite sprite;

    public SpriteComponent(Sprite sprite)
    {
        this.sprite = sprite;
    }

    @Override
    protected void onInit()
    {
        registerEventListener(RenderEvent.class, this::onRender);
    }

    @EventListener
    private void onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawRectangle(t.getPositionX(), t.getPositionY(), t.getPositionZ(), t.getScaleX(), t.getScaleY(),
                sprite);
    }

    public static final class Blueprint implements IComponentBlueprint<SpriteComponent>
    {
        private final Sprite sprite;

        public Blueprint(Sprite sprite)
        {
            this.sprite = sprite;
        }

        @Override
        public SpriteComponent createComponent()
        {
            return new SpriteComponent(sprite);
        }

        @Override
        public void serialize(Map<String, Object> fields)
        {
            fields.put("sprite", sprite);
        }

        @Override
        public String getUniqueKey()
        {
            return sprite.getUniqueKey();
        }

        @Deserializer
        public static Blueprint deserialize(Map<String, String> fields)
        {
            return new Blueprint(SerializablePool.deserialize(fields.get("sprite")));
        }
    }
}
