package com.github.fahjulian.stealth.events.mouse;

public class MouseButtonReleasedEvent extends AMouseEvent
{
    private final Button button;

    public MouseButtonReleasedEvent(float x, float y, Button button)
    {
        super(x, y);
        this.button = button;
        dispatch();
    }

    public Button getButton()
    {
        return button;
    }

    @Override
    public String toString()
    {
        return String.format("MouseButtonReleasedEvent at (%f, %f), button: %s", x, y,
                button.toString());
    }

}
