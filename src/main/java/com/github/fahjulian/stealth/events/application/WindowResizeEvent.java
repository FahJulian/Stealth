package com.github.fahjulian.stealth.events.application;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public class WindowResizeEvent extends AbstractEvent
{
    private float x, y;

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }
}
