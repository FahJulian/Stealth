package com.github.fahjulian.stealth.ui;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.ui.UIConstraint.Type;

public class UIComponent implements IUIParent
{
    private final UIConstraints constraints;
    private final Color color;
    private final IUIParent parent;
    private final UILayer layer;
    private final EventDispatcher eventDispatcher;

    protected boolean hovered;

    public UIComponent(IUIParent parent, UIConstraints constraints, Color color)
    {
        this.constraints = constraints;
        this.color = color;
        this.parent = parent;
        this.layer = (parent instanceof UIComponent) ? ((UIComponent) parent).layer : (UILayer) parent;
        this.eventDispatcher = new EventDispatcher(null);
    }

    protected void onInit()
    {
    }

    protected <E extends AbstractEvent> void registerEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        eventDispatcher.registerEventListener(eventClass, listener);
    }

    void init()
    {
        layer.getEventDispatcher().registerSubDispatcher(eventDispatcher);
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

    public UIConstraints getConstraints()
    {
        return constraints;
    }

    public Color getColor()
    {
        return color;
    }

    @Override
    public float getX()
    {
        UIConstraint c = constraints.getX();
        if (c.getType() == Type.PIXELS)
        {
            return parent.getX() + c.getValue();
        }
        else if (c.getType() == Type.RELATIVE)
        {
            return parent.getX() + c.getValue() * parent.getWidth();
        }

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getX().", c.getType());
        return 0;
    }

    @Override
    public float getY()
    {
        UIConstraint c = constraints.getY();
        if (c.getType() == Type.PIXELS)
        {
            return parent.getY() + c.getValue();
        }
        else if (c.getType() == Type.RELATIVE)
        {
            return parent.getY() + c.getValue() * parent.getHeight();
        }

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getY().", c.getType());
        return 0;
    }

    @Override
    public float getWidth()
    {
        UIConstraint c = constraints.getWidth();
        if (c.getType() == Type.PIXELS)
        {
            return c.getValue();
        }
        else if (c.getType() == Type.RELATIVE)
        {
            return c.getValue() * parent.getWidth();
        }

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getWidth().", c.getType());
        return 0;
    }

    @Override
    public float getHeight()
    {
        UIConstraint c = constraints.getHeight();
        if (c.getType() == Type.PIXELS)
        {
            return c.getValue();
        }
        else if (c.getType() == Type.RELATIVE)
        {
            return c.getValue() * parent.getHeight();
        }

        Log.warn("(UIComponent) UIConstraint.Type %s is not implemented in UIComponent.getHeight().", c.getType());
        return 0;
    }

    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }
}
