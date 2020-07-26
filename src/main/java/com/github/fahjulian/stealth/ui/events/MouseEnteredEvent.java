package com.github.fahjulian.stealth.ui.events;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.ui.UIComponent;

public class MouseEnteredEvent extends AbstractEvent
{
    public MouseEnteredEvent(UIComponent component)
    {
        super.dispatcher = component.getEventDispatcher();
        super.dispatch();
    }
}
