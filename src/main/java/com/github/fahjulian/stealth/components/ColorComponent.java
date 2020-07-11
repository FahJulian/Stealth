package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.IComponentBlueprint;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

/**
 * Renderes a 2D Rectangle in a given color at the entities position
 */
public class ColorComponent extends AbstractComponent
{
    public static final class Blueprint implements IComponentBlueprint<ColorComponent>
    {
        private final Color color;

        public Blueprint(Color color)
        {
            this.color = color;
        }

        public ColorComponent createComponent()
        {
            return new ColorComponent(color);
        }
    }

    private final Color color;

    public ColorComponent(Color color)
    {
        this.color = color;
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
                color);
    }
}
