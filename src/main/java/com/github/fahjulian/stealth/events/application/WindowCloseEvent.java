package com.github.fahjulian.stealth.events.application;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

/** Raised by the Window class when the window is being closed */
public class WindowCloseEvent extends AbstractEvent
{
    public WindowCloseEvent()
    {
        dispatch();
    }
}
