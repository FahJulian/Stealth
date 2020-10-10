package com.github.fahjulian.stealth.core.window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.Collections;
import java.util.HashMap;

public enum Key {
    SPACE(GLFW_KEY_SPACE), //
    W(GLFW_KEY_W), //
    A(GLFW_KEY_A), //
    S(GLFW_KEY_S), // ,
    D(GLFW_KEY_D), //
    CONTROL_LEFT(GLFW_KEY_LEFT_CONTROL), //
    ESCAPE(GLFW_KEY_ESCAPE);

    private final int glfwID;

    private static HashMap<Integer, Key> keysByGlfwID;

    private Key(int glfwID) {
        this.glfwID = glfwID;
        Key.register(this);
    }

    int getID() {
        return glfwID;
    }

    static Key getKeyByID(int glfwID) {
        return keysByGlfwID.get(glfwID);
    }

    static int getHighestKey() {
        return Collections.max(keysByGlfwID.keySet());
    }

    private static void register(Key key) {
        if (keysByGlfwID == null)
            keysByGlfwID = new HashMap<>();
        else if (!keysByGlfwID.containsKey(key.glfwID))
            keysByGlfwID.put(key.glfwID, key);
    }
}
