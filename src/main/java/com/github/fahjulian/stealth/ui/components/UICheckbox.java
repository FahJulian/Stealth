package com.github.fahjulian.stealth.ui.components;

import static com.github.fahjulian.stealth.graphics.Color.DARK_GREY;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_GREY;
import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;
import static com.github.fahjulian.stealth.ui.constraint.Type.RELATIVE;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_MATERIAL;

import java.util.function.Consumer;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.mouse.AbstractMouseEvent.Button;
import com.github.fahjulian.stealth.graphics.IMaterial;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.ui.IUIComponent;
import com.github.fahjulian.stealth.ui.UIBorder;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.constraint.UIConstraint;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;
import com.github.fahjulian.stealth.ui.events.UIComponentClickedEvent;
import com.github.fahjulian.stealth.ui.property.Type;
import com.github.fahjulian.stealth.ui.property.UIProperty;

public class UICheckbox extends UIComponent
{
    public static final Type<IMaterial> INNER_MATERIAL = new Type<>();
    public static final Type<UIBorder> INNER_BORDER = new Type<>();

    private final Consumer<Boolean> activeListener;
    private boolean active;

    public UICheckbox(IUIComponent parent, UIConstraints constraints, float i1, float i2,
            Consumer<Boolean> activeListener)
    {
        this(parent, constraints, i1, i2, DARK_GREY, LIGHT_GREY, DARK_GREY, activeListener);
    }

    public UICheckbox(IUIComponent parent, UIConstraints constraints, float i1, float i2, IMaterial outerBorder,
            IMaterial innerBorder, IMaterial inner, Consumer<Boolean> activeListener)
    {
        super(parent, constraints, new UIProperty<>(PRIMARY_MATERIAL, innerBorder),
                new UIProperty<>(BORDER, new UIBorder(outerBorder, i1)), new UIProperty<>(INNER_MATERIAL, inner),
                new UIProperty<>(INNER_BORDER, new UIBorder(innerBorder, i2)));

        this.activeListener = activeListener;
    }

    @Override
    protected void onInit()
    {
        super.registerEventListener(UIComponentClickedEvent.class, (e) ->
        {
            if (e.getButton() == Button.LEFT)
                this.activeListener.accept(this.active = !this.active);
        });
    }

    @Override
    protected void onRender(RenderEvent event)
    {
        super.onRender(event);

        if (this.active)
        {
            float x = super.getX() + super.getBorderWidth(), y = super.getY() + super.getBorderHeight(),
                    z = super.getZ();
            float width = super.getWidth() - 2 * super.getBorderWidth(),
                    height = super.getHeight() - 2 * super.getBorderHeight();

            if (super.getProperties().get(INNER_BORDER) != null)
            {
                float bWidth = this.getInnerBorderWidth(), bHeight = this.getInnerBorderHeight();
                Renderer2D.drawStaticRectangle(x, y, z + 0.01f, width, height,
                        super.getProperties().get(INNER_BORDER).getMaterial());
                Renderer2D.drawStaticRectangle(x + bWidth, y + bHeight, z + 0.011f, width - 2 * bWidth,
                        height - 2 * bHeight, super.getProperties().get(INNER_MATERIAL));
            }
            else
            {
                Renderer2D.drawStaticRectangle(x, y, z + 0.01f, width, height,
                        super.getProperties().get(INNER_MATERIAL));
            }
        }
    }

    private float getInnerBorderWidth()
    {
        UIConstraint c = properties.get(INNER_BORDER).getWidth();
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return getWidth() * c.getValue();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }

    private float getInnerBorderHeight()
    {
        UIConstraint c = properties.get(INNER_BORDER).getHeight();
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return getHeight() * c.getValue();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }
}
