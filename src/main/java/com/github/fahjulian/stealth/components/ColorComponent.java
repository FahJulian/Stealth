package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Renderer2D;

import org.joml.Vector4f;

public class ColorComponent extends AComponent
{
    private final Vector4f color;

    public ColorComponent(Vector4f color)
    {
        this.color = color;
    }

    @Override
    public void onInit()
    {
        addEventListener(RenderEvent.class, this::onRender);
    }

    public boolean onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawRectangle(t.getPosition(), t.getScale(), t.getRotation(), color);
        return false;
    }
}
