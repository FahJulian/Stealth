package com.github.fahjulian.stealth.ui.events;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.ui.UIComponent;

public class UIComponentMouseEnteredEvent extends AbstractEvent
{
    public UIComponentMouseEnteredEvent(UIComponent component)
    {
        super.dispatcher = component.getEventDispatcher();
        super.dispatch();
    }
}
