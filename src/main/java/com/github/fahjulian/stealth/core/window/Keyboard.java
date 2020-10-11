package com.github.fahjulian.stealth.core.window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.key.KeyPressedEvent;
import com.github.fahjulian.stealth.core.event.key.KeyReleasedEvent;
import com.github.fahjulian.stealth.core.util.Log;

public final class Keyboard {

    private boolean[] pressedKeys;

    public static final EventDispatcher<KeyPressedEvent> onKeyPressed;
    public static final EventDispatcher<KeyReleasedEvent> onKeyReleased;

    private static Keyboard instance;

    static {
        onKeyPressed = new EventDispatcher<>();
        onKeyReleased = new EventDispatcher<>();
    }

    public static Keyboard init() {
        assert instance == null : Log.coreError("Keyboard", "Keyboard can't be initialized more than once.");
        instance = new Keyboard();
        instance.pressedKeys = new boolean[Key.getHighestKey() + 1];
        return instance;
    }

    public static boolean isKeyPressed(Key key) {
        return instance.pressedKeys[key.getID()];
    }

    void keyCallback(long glfwWindow, int keyID, int scancode, int action, int mods) {
        Key key = Key.getKeyByID(keyID);

        if (action == GLFW_PRESS) {
            // TODO: Create Event
            onKeyPressed.dispatch(new KeyPressedEvent(key));
            pressedKeys[keyID] = true;
        } else if (action == GLFW_RELEASE) {
            // TODO: Create Event
            pressedKeys[keyID] = false;
        }

        Log.coreInfo("Keyboard", "Key event with key %s", key);
    }
}
