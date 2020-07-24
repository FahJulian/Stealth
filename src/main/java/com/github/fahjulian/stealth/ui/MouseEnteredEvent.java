package com.github.fahjulian.stealth.ui;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public class MouseEnteredEvent extends AbstractEvent
{
    public MouseEnteredEvent(UIComponent component)
    {
        super.dispatcher = component.getEventDispatcher();
        super.dispatch();
    }
}
