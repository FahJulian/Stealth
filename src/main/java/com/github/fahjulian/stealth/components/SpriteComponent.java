package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.IComponentBlueprint;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

/**
 * Renders a given sprite at the entities position
 */
public class SpriteComponent extends AbstractComponent
{
    public static final class Blueprint implements IComponentBlueprint<SpriteComponent>
    {
        private final Texture2D texture;

        public Blueprint(Texture2D texture)
        {
            this.texture = texture;
        }

        @Override
        public SpriteComponent createComponent()
        {
            return new SpriteComponent(texture);
        }
    }

    private final Texture2D texture;

    public SpriteComponent(Texture2D texture)
    {
        this.texture = texture;
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
                texture);
    }
}
