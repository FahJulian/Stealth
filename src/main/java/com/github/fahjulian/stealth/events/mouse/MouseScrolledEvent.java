package com.github.fahjulian.stealth.events.mouse;

public class MouseScrolledEvent extends AMouseEvent
{
    private final float scrollX, scrollY;

    public MouseScrolledEvent(float x, float y, float scrollX, float scrollY)
    {
        super(x, y);
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        dispatch();
    }

    public float getScrollX()
    {
        return scrollX;
    }

    public float getScrollY()
    {
        return scrollY;
    }

    @Override
    public String toString()
    {
        return String.format("MouseScrolledEvent at (%f, %f), scroll: (%f, %f)", x, y, scrollX,
                scrollY);
    }
}
