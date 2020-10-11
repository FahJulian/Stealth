package com.github.fahjulian.stealth.core.window;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
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

import com.github.fahjulian.stealth.core.util.Log;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Window {

    private long glfwID;
    private String title;
    private int width, height;
    private final Mouse mouse;
    private final Keyboard keyboard;

    private static Window instance;

    private Window() {
        this.mouse = Mouse.init();
        this.keyboard = Keyboard.init();
    }

    public static Window get() {
        return instance != null ? instance : (instance = new Window());
    }

    public static Window init(String title, int width, int height) {
        Window.instance = new Window();
        Window.instance.title = title;
        Window.instance.width = width;
        Window.instance.height = height;

        glfwSetErrorCallback((error, description) -> Log.coreError("Window", "GLFW Error: %s",
                GLFWErrorCallback.getDescription(description)));
        assert glfwInit() : Log.coreError("Window", "Unable to Initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        instance.glfwID = glfwCreateWindow(width, height, title, NULL, NULL);
        assert instance.glfwID != NULL : Log.coreError("Window", "Failed to create GLFW Window.");

        glfwSetCursorPosCallback(instance.glfwID, instance.mouse::cursorPosCallback);
        glfwSetMouseButtonCallback(instance.glfwID, instance.mouse::buttonCallback);
        glfwSetScrollCallback(instance.glfwID, instance.mouse::scrollCallback);
        glfwSetKeyCallback(instance.glfwID, instance.keyboard::keyCallback);
        glfwSetWindowCloseCallback(instance.glfwID, (glfwWindow) -> Log.coreInfo("Window", "Window close event.")); // TODO:
                                                                                                                    // Create
                                                                                                                    // event

        glfwMakeContextCurrent(instance.glfwID);
        glfwSwapInterval(1);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);

        Log.coreInfo("Window", "Initialized window.");
        return instance;
    }

    public static void setVisible(boolean visible) {
        if (visible)
            glfwShowWindow(instance.glfwID);
        else
            glfwHideWindow(instance.glfwID);
    }

    public static void destroy() {
        glfwFreeCallbacks(instance.glfwID);
        glfwDestroyWindow(instance.glfwID);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    public static void swapBuffers() {
        glfwSwapBuffers(instance.glfwID);
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static boolean isClosed() {
        return glfwWindowShouldClose(instance.glfwID);
    }

    public static float getTime() {
        return (float) glfwGetTime();
    }

    public static String getTitle() {
        return instance.title;
    }

    public static void setTitle(String title) {
        glfwSetWindowTitle(instance.glfwID, instance.title = title);
    }

    public static float getWidth() {
        return instance.width;
    }

    public static float getHeight() {
        return instance.height;
    }
}
