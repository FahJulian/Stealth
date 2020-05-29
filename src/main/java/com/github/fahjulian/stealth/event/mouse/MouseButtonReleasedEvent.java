package com.github.fahjulian.stealth.event.mouse;

public class MouseButtonReleasedEvent extends AMouseEvent {

    public final Button button;

    public MouseButtonReleasedEvent(float x, float y, Button button) {
        super(x, y);
        this.button = button;
        dispatch();
    }

    @Override
    public String toString() {
        return String.format("MouseButtonReleasedEvent at (%f, %f), button: %s", x, y, button.toString());
    }
    
}