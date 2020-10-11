package com.github.fahjulian.stealth.core.event.mouse;

import com.github.fahjulian.stealth.core.event.Event;
import com.github.fahjulian.stealth.core.window.Button;

public class MouseButtonPressedEvent extends Event {

    public final Button button;
    public final float x, y;

    public MouseButtonPressedEvent(Button button, float x, float y) {
        this.button = button;
        this.x = x;
        this.y = y;
    }
}
