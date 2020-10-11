package com.github.fahjulian.stealth.core.event.mouse;

import com.github.fahjulian.stealth.core.event.Event;

public class MouseScrolledEvent extends Event {

    public final float x, y;
    public final float scrollX, scrollY;

    public MouseScrolledEvent(float x, float y, float scrollX, float scrollY) {
        this.x = x;
        this.y = y;
        this.scrollX = scrollX;
        this.scrollY = scrollY;
    }
}
