package com.github.fahjulian.stealth.ui.components;

import static com.github.fahjulian.stealth.graphics.Color.DARK_BLUE;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_BLUE;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_GREY;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_MATERIAL;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_MATERIAL;
import static com.github.fahjulian.stealth.ui.property.Types.SECONDARY_MATERIAL;

import java.util.function.Consumer;

import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.mouse.AbstractMouseEvent.Button;
import com.github.fahjulian.stealth.events.mouse.MouseButtonReleasedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.graphics.IMaterial;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.ui.IUIComponent;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;
import com.github.fahjulian.stealth.ui.events.UIComponentClickedEvent;

public class UISlider extends UIComponent
{
    private final Consumer<Float> sliderPosCallback;
    private final float sliderWidth, barHeight;

    private boolean dragged;
    private float sliderPos;

    public UISlider(IUIComponent parent, UIConstraints constraints, float sliderWidth, float barHeight,
            Consumer<Float> sliderPosCallback)
    {
        super(parent, constraints);
        super.properties.set(PRIMARY_MATERIAL, LIGHT_BLUE);
        super.properties.set(SECONDARY_MATERIAL, LIGHT_GREY);
        super.properties.set(HOVER_MATERIAL, DARK_BLUE);

        this.sliderWidth = sliderWidth;
        this.barHeight = barHeight;
        this.sliderPos = super.getWidth() / 2;
        this.sliderPosCallback = sliderPosCallback;
        sliderPosCallback.accept(sliderPos / super.getWidth());
    }

    @Override
    protected void onInit()
    {
        registerEventListener(UIComponentClickedEvent.class, (e) ->
        {
            if (e.getButton() == Button.LEFT && hovered)
                dragged = true;
        });
        registerEventListener(MouseButtonReleasedEvent.class, (e) ->
        {
            if (e.getButton() == Button.LEFT)
                dragged = false;
        });
        registerEventListener(MouseMovedEvent.class, (e) ->
        {
            if (dragged)
            {
                float width = super.getWidth();
                sliderPos = Toolbox.clamp(sliderPos + e.getDeltaX(), 0, width);
                sliderPosCallback.accept(sliderPos / width);
            }
        });
    }

    @Override
    public IMaterial getMaterial()
    {
        return hovered || dragged ? properties.get(HOVER_MATERIAL) : properties.get(PRIMARY_MATERIAL);
    }

    @Override
    protected void onRender(RenderEvent event)
    {
        if (!layer.isActive())
            return;

        IMaterial material = this.getMaterial();
        IMaterial barMaterial = super.getProperties().get(SECONDARY_MATERIAL);
        float x = super.getX(), y = super.getY(), z = super.getZ(), width = super.getWidth(),
                height = super.getHeight();

        Renderer2D.drawStaticRectangle(x + sliderPos - sliderWidth / 2, y, z, sliderWidth, height, material);
        Renderer2D.drawStaticRectangle(x, y + (height - barHeight) / 2, z, width, barHeight, barMaterial);
    }
}
