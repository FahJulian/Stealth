package com.github.fahjulian.stealth.ui;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public class UIComponentHoverEvent extends AbstractEvent
{
    public UIComponentHoverEvent(UIComponent component)
    {
        this.dispatcher = component.getEventDispatcher();

        dispatch();
    }
}
