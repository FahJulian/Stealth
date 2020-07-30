package com.github.fahjulian.stealth.ui.events;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.ui.UIComponent;

public class UIComponentMouseExitedEvent extends AbstractEvent
{
    public UIComponentMouseExitedEvent(UIComponent component)
    {
        super.dispatcher = component.getEventDispatcher();
        super.dispatch();
    }
}
