package com.github.fahjulian.stealth.ui.events;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.events.mouse.AbstractMouseEvent.Button;
import com.github.fahjulian.stealth.ui.UIComponent;

public class UIComponentClickedEvent extends AbstractEvent
{
    private final Button button;

    public UIComponentClickedEvent(Button button, UIComponent component)
    {
        this.button = button;
        super.dispatcher = component.getEventDispatcher();

        dispatch();
    }

    public Button getButton()
    {
        return button;
    }
}
