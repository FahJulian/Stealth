package com.github.fahjulian.stealth.ui;

import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;
import static com.github.fahjulian.stealth.ui.constraint.Type.RELATIVE;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER_COLOR;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER_HOVER_COLOR;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER_SIZE;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_COLOR;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_COLOR;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.ui.constraint.UIConstraint;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;
import com.github.fahjulian.stealth.ui.events.MouseEnteredEvent;
import com.github.fahjulian.stealth.ui.events.MouseExitedEvent;
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
        eventDispatcher.registerEventListener(RenderEvent.class, this::onRender);
        eventDispatcher.registerEventListener(MouseEnteredEvent.class, (e) ->
        {
            hovered = true;
        });
        eventDispatcher.registerEventListener(MouseExitedEvent.class, (e) ->
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
        eventDispatcher.registerEventListener(eventClass, listener);
    }

    protected void onRender(RenderEvent event)
    {
        float x = this.getX(), y = this.getY(), z = layer.getPosZ(), width = this.getWidth(), height = this.getHeight();
        Color color = this.getColor();

        if (this.getProperties().get(BORDER_SIZE).getValue() != 0)
        {
            Color bColor = this.getBorderColor();
            float bWidth = this.getBorderWidth(), bHeight = this.getBorderHeight();
            Renderer2D.drawStaticRectangle(x, y, z, width, height, bColor);
            Renderer2D.drawStaticRectangle(x + bWidth, y + bHeight, z + 0.01f, width - 2 * bWidth, height - 2 * bHeight,
                    color);
        }
        else
        {
            Renderer2D.drawStaticRectangle(x, y, z, width, height, color);
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
        UIConstraint c = properties.get(BORDER_SIZE);
        if (c.getType() == PIXELS)
            return c.getValue();
        else if (c.getType() == RELATIVE)
            return getWidth() * c.getValue();

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }

    public float getBorderHeight()
    {
        UIConstraint c = properties.get(BORDER_SIZE);
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

    public Color getColor()
    {
        return hovered ? properties.get(HOVER_COLOR) : properties.get(PRIMARY_COLOR);
    }

    public Color getBorderColor()
    {
        return hovered ? properties.get(BORDER_HOVER_COLOR) : properties.get(BORDER_COLOR);
    }
}
