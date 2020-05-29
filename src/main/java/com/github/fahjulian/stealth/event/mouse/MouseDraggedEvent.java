package com.github.fahjulian.stealth.event.mouse;

public class MouseDraggedEvent extends AMouseEvent {

    public final Button button;

    public MouseDraggedEvent(float x, float y, Button button) {
        super(x, y);
        this.button = button;
        dispatch();
    }

    @Override
    public String toString() {
        return String.format("MouseDraggedEvent at (%f, %f), button: %s", x, y, button.toString());
    }
}
