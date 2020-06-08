package com.github.fahjulian.stealth.events.mouse;

import com.github.fahjulian.stealth.core.event.AEvent;

public abstract class AMouseEvent extends AEvent {

    protected final float x, y;

    protected AMouseEvent(float posX, float posY) {
        this.x = posX;
        this.y = posY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static enum Button {
        LEFT,
        MIDDLE,
        RIGHT,
        UNKNOWN;
    }
}