package com.github.fahjulian.stealth.ui;

import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;
import static com.github.fahjulian.stealth.ui.constraint.Type.RELATIVE;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_BORDER;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_MATERIAL;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_MATERIAL;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.IMaterial;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.ui.constraint.UIConstraint;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;
import com.github.fahjulian.stealth.ui.events.UIComponentMouseEnteredEvent;
import com.github.fahjulian.stealth.ui.events.UIComponentMouseExitedEvent;
import com.github.fahjulian.stealth.ui.property.UIProperties;
import com.github.fahjulian.stealth.ui.property.UIProperty;

public class UIComponent implements IUIComponent
{
    protected final UIConstraints constraints;
    protected final IUIComponent parent;
    protected final AbstractUILayer<?> layer;
    private final EventDispatcher eventDispatcher;

    protected UIProperties properties;
    protected boolean hovered;
    protected boolean hidden;

    public UIComponent(IUIComponent parent, UIConstraints constraints, UIProperty<?>... properties)
    {
        this.constraints = constraints;
        this.parent = parent;
        this.layer = (parent instanceof UIComponent) ? ((UIComponent) parent).layer : (AbstractUILayer<?>) parent;

        this.eventDispatcher = new EventDispatcher(null);
        this.properties = new UIProperties(properties);
    }

    void init()
    {
        layer.getEventDispatcher().registerSubDispatcher(eventDispatcher);
        this.registerEventListener(RenderEvent.class, this::onRender);
        this.registerEventListener(UIComponentMouseEnteredEvent.class, (e) ->
        {
            hovered = true;
        });
        this.registerEventListener(UIComponentMouseExitedEvent.class, (e) ->
        {
            hovered = false;
        });

        onInit();
    }

    protected void onInit()
    {
    }

    protected <E extends AbstractEvent> void registerEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        eventDispatcher.registerEventListener(eventClass, (e) ->
        {
            if (!hidden && layer.isActive())
                listener.onEvent(e);
        });
    }

    protected void onRender(RenderEvent event)
    {
        IMaterial material = this.getMaterial();
        if (material == null)
            return;

        float x = this.getX(), y = this.getY(), z = this.getZ(), width = this.getWidth(), height = this.getHeight();

        if (this.getProperties().get(BORDER) != null)
        {
            IMaterial bMaterial = this.getBorder().getMaterial();
            float bWidth = this.getBorderWidth(), bHeight = this.getBorderHeight();
            Renderer2D.drawStaticRectangle(x, y, z, width, height, bMaterial);
            Renderer2D.drawStaticRectangle(x + bWidth, y + bHeight, z + 0.01f, width - 2 * bWidth, height - 2 * bHeight,
                    material);
        }
        else
        {
            Renderer2D.drawStaticRectangle(x, y, z, width, height, material);
        }
    }

    @Override
    public float getX()
    {
        UIConstraint c = constraints.getX();
        if (c.getType() == PIXELS)
            return parent.getX() + c.getValue();
        else if (c.getType() == RELATIVE)
            return parent.getX() + c.getValue() * parent.getWidth();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getX().", c.getType());
        return 0;
    }

    @Override
    public float getY()
    {
        UIConstraint c = constraints.getY();
        if (c.getType() == PIXELS)
            return parent.getY() + c.getValue();
        else if (c.getType() == RELATIVE)
            return parent.getY() + c.getValue() * parent.getHeight();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getY().", c.getType());
        return 0;
    }

    @Override
    public float getWidth()
    {
        UIConstraint c = constraints.getWidth();
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return c.getValue() * parent.getWidth();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getWidth().", c.getType());
        return 0;
    }

    public float getZ()
    {
        return constraints.getZ();
    }

    @Override
    public float getHeight()
    {
        UIConstraint c = constraints.getHeight();
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return c.getValue() * parent.getHeight();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }

    public float getBorderWidth()
    {
        UIConstraint c = properties.get(BORDER).getWidth();
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return getWidth() * c.getValue();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }

    public float getBorderHeight()
    {
        UIConstraint c = properties.get(BORDER).getHeight();
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return getHeight() * c.getValue();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }

    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }

    public UIConstraints getConstraints()
    {
        return constraints;
    }

    public UIProperties getProperties()
    {
        return properties;
    }

    public IMaterial getMaterial()
    {
        return hovered && properties.get(HOVER_MATERIAL) != null ? properties.get(HOVER_MATERIAL)
                : properties.get(PRIMARY_MATERIAL);
    }

    public UIBorder getBorder()
    {
        return hovered && properties.get(HOVER_BORDER) != null ? properties.get(HOVER_BORDER) : properties.get(BORDER);
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }
}
