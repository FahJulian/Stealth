package com.github.fahjulian.stealth.core.window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import com.github.fahjulian.stealth.core.util.Log;

public final class Mouse {

    private boolean[] pressedButtons;
    private float x, y;

    private static Mouse instance;

    public static Mouse init() {
        assert instance == null : Log.coreError("Mouse", "Mouse can't be initialized more than once.");
        instance = new Mouse();
        instance.pressedButtons = new boolean[Button.getHighestKey()];
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
                Log.coreInfo("Mouse", "Mouse dragged with button %s: (%f, %f)", button, x, y);
                // TODO: Create mouse dragged event
            }
        }

        // TODO: Create mouse moved event
        Log.coreInfo("Mouse", "Mouse moved: (%f, %f)", x, y);
    }

    void scrollCallback(long glfwWindow, double scrollX, double scrollY) {
        // TODO: Create mouse scrolled event
        Log.coreInfo("Mouse", "Mouse scrolled: (%f, %f)", scrollX, scrollY);
    }

    void buttonCallback(long glfwWindow, int buttonID, int action, int mods) {
        Button button = Button.getButtonByID(buttonID);

        if (action == GLFW_PRESS) {
            pressedButtons[buttonID] = true;
            // TODO: Create event
            Log.coreInfo("Mouse", "Mouse button pressed: %s", button);
        } else if (action == GLFW_RELEASE) {
            pressedButtons[buttonID] = false;
            // TODO: Create event
            Log.coreInfo("Mouse", "Mouse button released: %s, button");
        }
    }
}
