package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Renderer2D;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class SpriteComponent extends AComponent
{
    private final Texture2D texture;

    public SpriteComponent(Texture2D texture)
    {
        this.texture = texture;
    }

    @Override
    protected void onInit()
    {
        addEventListener(RenderEvent.class, this::onRender);
    }

    private boolean onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawRectangle(t.getPositionX(), t.getPositionY(), t.getPositionZ(), t.getScaleX(), t.getScaleY(),
                texture);
        return false;
    }
}
