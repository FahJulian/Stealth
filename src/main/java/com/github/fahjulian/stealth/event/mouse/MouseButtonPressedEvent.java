package com.github.fahjulian.stealth.event.mouse;

public class MouseButtonPressedEvent extends AMouseEvent {

    public final Button button;

    public MouseButtonPressedEvent(float x, float y, Button button) {
        super(x, y);
        this.button = button;
    }

    @Override
    public String toString() {
        return String.format("MouseButtonPressedEvent at (%f, %f), button: %s", x, y, button.toString());
    }

 }