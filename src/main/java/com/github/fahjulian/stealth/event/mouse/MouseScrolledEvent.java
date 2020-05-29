package com.github.fahjulian.stealth.event.mouse;

public class MouseScrolledEvent extends AMouseEvent {

    public final float scrollX, scrollY;

    public MouseScrolledEvent(float x, float y, float scrollX, float scrollY) {
        super(x, y);
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        dispatch();
    }

    @Override
    public String toString() {
        return String.format("MouseScrolledEvent at (%f, %f), scroll: (%f, %f)", x, y, scrollX, scrollY);
    }
}
