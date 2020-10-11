package com.github.fahjulian.stealth.core.event.mouse;

import com.github.fahjulian.stealth.core.event.Event;
import com.github.fahjulian.stealth.core.window.Button;

public class MouseDraggedEvent extends Event {

    public final Button button;
    public final float x, y;
    public final float deltaX, deltaY;

    public MouseDraggedEvent(Button button, float x, float y, float deltaX, float deltaY) {
        this.button = button;
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
