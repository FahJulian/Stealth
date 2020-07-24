package com.github.fahjulian.stealth.ui;

import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.graphics.Color;

public class UIButton extends UIComponent
{
    private final IEventListener<UIComponentClickedEvent> clickListener;

    public UIButton(IUIParent parent, UIConstraints constraints, IEventListener<UIComponentClickedEvent> clickListener)
    {
        super(parent, constraints, Color.WHITE);
        this.clickListener = clickListener;
    }

    @Override
    protected void onInit()
    {
        registerEventListener(UIComponentClickedEvent.class, clickListener);
    }
}
