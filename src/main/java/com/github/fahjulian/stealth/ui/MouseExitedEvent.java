package com.github.fahjulian.stealth.ui;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public class MouseExitedEvent extends AbstractEvent
{
    public MouseExitedEvent(UIComponent component)
    {
        super.dispatcher = component.getEventDispatcher();
        super.dispatch();
    }
}
