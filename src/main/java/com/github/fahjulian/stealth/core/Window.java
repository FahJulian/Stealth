package com.github.fahjulian.stealth.core;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_3;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.WindowCloseEvent;
import com.github.fahjulian.stealth.events.key.AbstractKeyEvent.Key;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.events.key.KeyReleasedEvent;
import com.github.fahjulian.stealth.events.mouse.AbstractMouseEvent;
import com.github.fahjulian.stealth.events.mouse.AbstractMouseEvent.Button;
import com.github.fahjulian.stealth.events.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseButtonReleasedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseScrolledEvent;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public final class Window
{
    private final GLFWInputListener inputListener;
    private String title;
    private int width, height;
    public long glfwID;

    private static Window instance;

    private Window()
    {
        inputListener = new GLFWInputListener();
    }

    /**
     * Retrieve the Window instance
     * 
     * @return The Window instance
     */
    public static Window get()
    {
        if (instance == null)
            instance = new Window();

        return instance;
    }

    /**
     * Initialize the Window instance
     * 
     * @param title
     *                   The title of the GLFW Window
     * @param width
     *                   The width of the GLFW Window
     * @param height
     *                   The height of the GLFW Window
     */
    public void init(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;

        glfwSetErrorCallback((error, description) ->
        {
            Log.error("(GLFW) Error: [%s]: %s", Integer.toHexString(error),
                    GLFWErrorCallback.getDescription(description));
        });

        if (!glfwInit())
        {
            Log.error("(Window) Unable to initialized GLFW.");
            return;
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // TODO Add window resizing
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        glfwID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwID == NULL)
        {
            Log.error("(Window) Failed to create GLFW Window.");
            return;
        }

        glfwSetCursorPosCallback(glfwID, inputListener::cursorPosCallback);
        glfwSetMouseButtonCallback(glfwID, inputListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwID, inputListener::scrollCallback);
        glfwSetKeyCallback(glfwID, inputListener::keyCallback);
        glfwSetWindowCloseCallback(glfwID, inputListener::windowCloseCallback);

        glfwMakeContextCurrent(glfwID);
        glfwSwapInterval(1);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);

        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

        Log.info("(Window) Initialized window.");
    }

    public void makeVisible()
    {
        glfwShowWindow(glfwID);
    }

    /**
     * Free all memory allocated to GLFW
     */
    public void destroy()
    {
        glfwFreeCallbacks(glfwID);
        glfwDestroyWindow(glfwID);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Allow GLFW Events to be polled. Should be called in the main loop.
     */
    public void pollEvents()
    {
        glfwPollEvents();
    }

    /**
     * Call glfwSwapBuffers
     */
    public void swapBuffers()
    {
        glfwSwapBuffers(glfwID);
    }

    public void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Check if a close event has been called in GLFW
     * 
     * @return Whether or not the window should close
     */
    public boolean isClosed()
    {
        return glfwWindowShouldClose(glfwID);
    }

    /**
     * @return The time in seconds that has passed since window creation
     */
    public float getTime()
    {
        return (float) glfwGetTime();
    }

    public String getInitialTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        glfwSetWindowTitle(glfwID, title);
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public boolean isKeyPressed(Key key)
    {
        return inputListener.pressedKeys[key.ID];
    }

    public boolean isMouseButtonPressed(Button button)
    {
        return inputListener.pressedButtons.contains(button);
    }

    public float getMouseX()
    {
        return inputListener.mouseX;
    }

    public float getMouseY()
    {
        return inputListener.mouseY;
    }

    private static class GLFWInputListener
    {
        private float mouseX, mouseY;
        private List<AbstractMouseEvent.Button> pressedButtons = new ArrayList<>();
        private boolean[] pressedKeys = new boolean[Key.getKeyAmount()];

        public void cursorPosCallback(long windowID, double posX, double posY)
        {
            float deltaX = (float) posX - mouseX;
            float deltaY = (float) posY - mouseY;
            this.mouseX = (float) posX;
            this.mouseY = (float) posY;

            for (AbstractMouseEvent.Button button : pressedButtons)
                new MouseDraggedEvent(this.mouseX, this.mouseY, deltaX, deltaY, button);

            new MouseMovedEvent(this.mouseX, this.mouseY, deltaX, deltaY);
        }

        public void mouseButtonCallback(long windowID, int buttonID, int action, int mods)
        {
            Button button = translateMouseButton(buttonID);

            if (action == GLFW_PRESS)
            {
                pressedButtons.add(button);
                new MouseButtonPressedEvent(this.mouseX, this.mouseY, button);
            }
            else if (action == GLFW_RELEASE)
            {
                pressedButtons.remove(button);
                new MouseButtonReleasedEvent(this.mouseX, this.mouseY, button);
            }
        }

        public void scrollCallback(long windowID, double offsetX, double offsetY)
        {
            new MouseScrolledEvent(this.mouseX, this.mouseY, (float) offsetX, (float) offsetY);
        }

        public void keyCallback(long window, int keyID, int scancode, int action, int mods)
        {
            Key key = translateKey(keyID);

            if (action == GLFW_PRESS)
            {
                new KeyPressedEvent(key);
                pressedKeys[key.ID] = true;
            }
            else if (action == GLFW_RELEASE)
            {
                new KeyReleasedEvent(key);
                pressedKeys[key.ID] = false;
            }
        }

        public void windowCloseCallback(long window)
        {
            new WindowCloseEvent();
        }

        private Button translateMouseButton(int glfwButtonID)
        {
            switch (glfwButtonID)
            {
            case GLFW_MOUSE_BUTTON_1:
                return Button.LEFT;
            case GLFW_MOUSE_BUTTON_2:
                return Button.RIGHT;
            case GLFW_MOUSE_BUTTON_3:
                return Button.MIDDLE;
            default:
                Log.warn("(Window) Unknown Mouse Button ID: %d", glfwButtonID);
                return Button.UNKNOWN;
            }
        }

        private Key translateKey(int glfwKeyID)
        {
            switch (glfwKeyID)
            {
            case GLFW_KEY_SPACE:
                return Key.SPACE;
            case GLFW_KEY_W:
                return Key.W;
            case GLFW_KEY_A:
                return Key.A;
            case GLFW_KEY_S:
                return Key.S;
            case GLFW_KEY_D:
                return Key.D;
            case GLFW_KEY_LEFT_CONTROL:
                return Key.CONTROL;
            case GLFW_KEY_ESCAPE:
                return Key.ESCAPE;
            default:
                Log.warn("(Window) Unknown GLFW Key ID: %d", glfwKeyID);
                return Key.UNKNOWN;
            }
        }
    }

}
