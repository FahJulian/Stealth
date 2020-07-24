package com.github.fahjulian.stealth.events.mouse;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public abstract class AbstractMouseEvent extends AbstractEvent
{
    protected final float x, y;

    protected AbstractMouseEvent(float posX, float posY)
    {
        this.x = posX;
        this.y = posY;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public static enum Button
    {
        LEFT, MIDDLE, RIGHT, UNKNOWN;
    }
}
