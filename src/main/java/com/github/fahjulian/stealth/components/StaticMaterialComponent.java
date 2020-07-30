package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.IMaterial;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

public class StaticMaterialComponent extends AbstractRenderComponent
{
    private IMaterial material;

    public StaticMaterialComponent(IMaterial material)
    {
        this.material = material;
    }

    @Override
    protected void onInit()
    {
        super.registerEventListener(RenderEvent.class, this::onRender);
    }

    private void onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawStaticRectangle(t.getPositionX(), t.getPositionY(), t.getPositionY(), t.getScaleX(),
                t.getScaleY(), this.material);
    }
}
