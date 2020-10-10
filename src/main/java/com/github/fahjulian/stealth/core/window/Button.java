package com.github.fahjulian.stealth.core.window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Button {
    LEFT(GLFW_MOUSE_BUTTON_1), //
    RIGHT(GLFW_MOUSE_BUTTON_2), //
    MIDDLE(GLFW_MOUSE_BUTTON_3);

    private final int glfwID;

    private static Map<Integer, Button> buttonsByGlfwID;

    private Button(int glfwID) {
        this.glfwID = glfwID;
        Button.register(this);
    }

    int getID() {
        return glfwID;
    }

    static Button getButtonByID(int glfwID) {
        return buttonsByGlfwID.get(glfwID);
    }

    static int getHighestKey() {
        return Collections.max(buttonsByGlfwID.keySet());
    }

    private static void register(Button button) {
        if (buttonsByGlfwID == null)
            buttonsByGlfwID = new HashMap<>();
        else if (!buttonsByGlfwID.containsKey(button.glfwID))
            buttonsByGlfwID.put(button.glfwID, button);
    }
}
