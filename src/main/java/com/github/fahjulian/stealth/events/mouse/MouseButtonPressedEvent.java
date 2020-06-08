package com.github.fahjulian.stealth.events.mouse;

public class MouseButtonPressedEvent extends AMouseEvent {

    private final Button button;

    public MouseButtonPressedEvent(float x, float y, Button button) {
        super(x, y);
        this.button = button;
        dispatch();
    }

    public Button getButton() {
        return button;
    }

    @Override
    public String toString() {
        return String.format("MouseButtonPressedEvent at (%f, %f), button: %s", x, y, button.toString());
    }

 }