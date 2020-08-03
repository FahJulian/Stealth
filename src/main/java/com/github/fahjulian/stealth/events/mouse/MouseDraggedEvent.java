package com.github.fahjulian.stealth.events.mouse;

public class MouseDraggedEvent extends AbstractMouseEvent
{
    private final Button button;
    private final float deltaX, deltaY;

    public MouseDraggedEvent(float x, float y, float deltaX, float deltaY, Button button)
    {
        super(x, y);

        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.button = button;

        dispatch();
    }

    public float getDeltaX()
    {
        return deltaX;
    }

    public float getDeltaY()
    {
        return deltaY;
    }

    public Button getButton()
    {
        return button;
    }

    @Override
    public String toString()
    {
        return String.format("MouseDraggedEvent at (%f, %f), button: %s", x, y, button.toString());
    }
}
