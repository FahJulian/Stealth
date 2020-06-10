package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.Renderer2D;

public class ColorComponent extends AComponent
{
    private final Color color;

    public ColorComponent(Color color)
    {
        this.color = color;
    }

    @Override
    protected void onInit()
    {
        addEventListener(RenderEvent.class, this::onRender);
    }

    private boolean onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawRectangle(t.getPositionX(), t.getPositionY(), t.getScaleX(), t.getScaleY(), t.getRotationZ(),
                color);
        return false;
    }
}
