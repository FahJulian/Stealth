package com.github.fahjulian.stealth.core.window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.core.event.mouse.MouseButtonReleasedEvent;
import com.github.fahjulian.stealth.core.event.mouse.MouseDraggedEvent;
import com.github.fahjulian.stealth.core.event.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.core.event.mouse.MouseScrolledEvent;
import com.github.fahjulian.stealth.core.util.Log;

public final class Mouse {

    private boolean[] pressedButtons;
    private float x, y;

    public static final EventDispatcher<MouseButtonPressedEvent> onMouseButtonPressed;
    public static final EventDispatcher<MouseButtonReleasedEvent> onMouseButtonReleased;
    public static final EventDispatcher<MouseDraggedEvent> onMouseDragged;
    public static final EventDispatcher<MouseMovedEvent> onMouseMoved;
    public static final EventDispatcher<MouseScrolledEvent> onMouseScrolled;

    private static Mouse instance;

    static {
        onMouseButtonPressed = new EventDispatcher<>();
        onMouseButtonReleased = new EventDispatcher<>();
        onMouseDragged = new EventDispatcher<>();
        onMouseMoved = new EventDispatcher<>();
        onMouseScrolled = new EventDispatcher<>();
    }

    public static Mouse init() {
        assert instance == null : Log.coreError("Mouse", "Mouse can't be initialized more than once.");
        instance = new Mouse();
        instance.pressedButtons = new boolean[Button.getHighestKey() + 1];
        return instance;
    }

    public static boolean isButtonPressed(Button button) {
        return instance.pressedButtons[button.getID()];
    }

    public static float getX() {
        return instance.x;
    }

    public static float getY() {
        return instance.y;
    }

    void cursorPosCallback(long glfwWindow, double posX, double posY) {
        float deltaX = (float) posX - this.x;
        float deltaY = (float) posY - this.y;
        this.x = (float) posX;
        this.y = (float) posY;

        for (int buttonID = 0; buttonID < pressedButtons.length; buttonID++) {
            if (pressedButtons[buttonID]) {
                Button button = Button.getButtonByID(buttonID);
                onMouseDragged.dispatch(new MouseDraggedEvent(button, x, y, deltaX, deltaY));
            }
        }

        onMouseMoved.dispatch(new MouseMovedEvent(x, y, deltaX, deltaY));
    }

    void scrollCallback(long glfwWindow, double scrollX, double scrollY) {
        onMouseScrolled.dispatch(new MouseScrolledEvent(getX(), getY(), (float) scrollX, (float) scrollY));
    }

    void buttonCallback(long glfwWindow, int buttonID, int action, int mods) {
        Button button = Button.getButtonByID(buttonID);

        if (action == GLFW_PRESS) {
            pressedButtons[buttonID] = true;
            onMouseButtonPressed.dispatch(new MouseButtonPressedEvent(button, getX(), getY()));
        } else if (action == GLFW_RELEASE) {
            pressedButtons[buttonID] = false;
            onMouseButtonReleased.dispatch(new MouseButtonReleasedEvent(button, getX(), getY()));
        }
    }
}
