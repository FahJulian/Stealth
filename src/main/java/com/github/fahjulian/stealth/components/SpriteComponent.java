package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.AbstractComponentBlueprint;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

/**
 * Renders a given sprite at the entities position
 */
public class SpriteComponent extends AbstractComponent
{
    public static final class Blueprint extends AbstractComponentBlueprint<SpriteComponent>
    {
        static
        {
            AbstractComponentBlueprint.register(Blueprint.class, Blueprint::deserialize);
        }

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
        public String serialize()
        {
            return "";
        }

        public static Blueprint deserialize(String xml)
        {
            return new Blueprint(null);
        }
    }

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

    private void onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawRectangle(t.getPositionX(), t.getPositionY(), t.getPositionZ(), t.getScaleX(), t.getScaleY(),
                sprite);
    }
}
