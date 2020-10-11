package com.github.fahjulian.stealth.core.event.mouse;

import com.github.fahjulian.stealth.core.event.Event;

public class MouseMovedEvent extends Event {

    public final float x, y;
    public final float deltaX, deltaY;

    public MouseMovedEvent(float x, float y, float deltaX, float deltaY) {
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
