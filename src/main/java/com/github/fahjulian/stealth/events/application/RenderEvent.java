package com.github.fahjulian.stealth.events.application;

import com.github.fahjulian.stealth.core.event.AEvent;

/** Event thrown every time the app should be rendered */
public class RenderEvent extends AEvent
{
    public RenderEvent()
    {
        dispatch();
    }

    @Override
    public String toString()
    {
        return "RenderEvent";
    }

}
