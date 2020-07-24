package com.github.fahjulian.stealth.events.mouse;

public class MouseMovedEvent extends AbstractMouseEvent
{
    private final float deltaX, deltaY;

    public MouseMovedEvent(float x, float y, float deltaX, float deltaY)
    {
        super(x, y);

        this.deltaX = deltaX;
        this.deltaY = deltaY;

        dispatch();
    }

    public float getDeltaX()
    {
        return deltaX;
    }

    public float getDeltaY()
    {
        return deltaY;
    }

    @Override
    public String toString()
    {
        return String.format("MouseMovedEvent at (%f, %f)", x, y);
    }
}
