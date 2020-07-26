package com.github.fahjulian.stealth.ui.components;

import static com.github.fahjulian.stealth.graphics.Color.DARK_GREY;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_COLOR;

import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.ui.IUIComponent;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;
import com.github.fahjulian.stealth.ui.events.UIComponentClickedEvent;

public class UIButton extends UIComponent
{
    private final IEventListener<UIComponentClickedEvent> clickListener;

    public UIButton(IUIComponent parent, UIConstraints constraints,
            IEventListener<UIComponentClickedEvent> clickListener)
    {
        super(parent, constraints);
        super.properties.set(HOVER_COLOR, DARK_GREY);

        this.clickListener = clickListener;
    }

    @Override
    protected void onInit()
    {
        registerEventListener(UIComponentClickedEvent.class, clickListener);
    }
}
