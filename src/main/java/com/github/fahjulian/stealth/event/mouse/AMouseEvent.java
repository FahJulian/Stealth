package com.github.fahjulian.stealth.event.mouse;

import com.github.fahjulian.stealth.event.AEvent;

public abstract class AMouseEvent extends AEvent {

    public final float x, y;

    protected AMouseEvent(float posX, float posY) {
        this.x = posX;
        this.y = posY;
    }

    public static enum Button {
        LEFT,
        MIDDLE,
        RIGHT;
    }
}