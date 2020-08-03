package com.github.fahjulian.stealth.ui.events;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.events.mouse.AbstractMouseEvent.Button;
import com.github.fahjulian.stealth.ui.UIComponent;

public class UIComponentClickedEvent extends AbstractEvent
{
    private final Button button;
    private final UIComponent component;

    public UIComponentClickedEvent(Button button, UIComponent component)
    {
        this.button = button;
        this.component = component;
        super.dispatcher = component.getEventDispatcher();

        super.dispatch();
    }

    public Button getButton()
    {
        return button;
    }

    public UIComponent getComponent()
    {
        return component;
    }
}
