package com.github.fahjulian.stealth.core;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.event.key.AKeyEvent;
import com.github.fahjulian.stealth.event.key.KeyPressedEvent;
import com.github.fahjulian.stealth.event.key.KeyReleasedEvent;
import com.github.fahjulian.stealth.event.mouse.AMouseEvent;
import com.github.fahjulian.stealth.event.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.event.mouse.MouseButtonReleasedEvent;
import com.github.fahjulian.stealth.event.mouse.MouseDraggedEvent;
import com.github.fahjulian.stealth.event.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.event.mouse.MouseScrolledEvent;
import com.github.fahjulian.stealth.scene.AScene;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public final class Window {
    
    private String title;
    private int width, height;
    private boolean initialized;
    private long glfwID;
    private AScene currentScene;

    private static Window instance;

    private Window() {
        initialized = false;
    }

    public static Window get() {
        if (instance == null)
            instance = new Window();

        return instance;
    }

    public void init(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        glfwSetErrorCallback((error, description) -> {
            Log.error("(GLFW) Error: [%s]: %s", Integer.toHexString(error), GLFWErrorCallback.getDescription(description));
        });

        if (!glfwInit()) {
            Log.error("(Window) Unable to initialized GLFW.");
            return;
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);         // TODO Add window resizing
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        glfwID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwID == NULL) {
            Log.error("(Window) Failed to create GLFW Window.");
            return;
        }

        GLFWInputListener inputListener = new GLFWInputListener();
        glfwSetCursorPosCallback(glfwID, inputListener::cursorPosCallback);
        glfwSetMouseButtonCallback(glfwID, inputListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwID, inputListener::scrollCallback);
        glfwSetKeyCallback(glfwID, inputListener::keyCallback);

        glfwMakeContextCurrent(glfwID);
        glfwSwapInterval(1);
        glfwShowWindow(glfwID);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        initialized = true;
        Log.info("(Window) Initialized window.");
    }

    public void start() {
        if (instance == null || !instance.initialized) {
            Log.error("(Window) Window must be initialized before calling the run() method.");
            return;
        }

        if (instance.currentScene == null) {
            Log.error("(Window) Must first set a scene before calling the run() method");
            return;
        }

        Log.info("(LWJGL) Using LWJGL Version %s", Version.getVersion());
    }

    public void delete() {
        glfwFreeCallbacks(glfwID);
        glfwDestroyWindow(glfwID);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public boolean isClosed() {
        return glfwWindowShouldClose(glfwID);
    }

    public void setScene(AScene scene) {
        if (scene == null) {
            Log.error("(Window) Cant set null as the windows scene.");
            return;
        }

        this.currentScene = scene;
        scene.init();
    }

    public String getInitialTitle() {
        return title;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(glfwID, title);
    }   
}


class GLFWInputListener {

    private float posX, posY;
    private List<AMouseEvent.Button> pressedButtons = new ArrayList<>();

    public void cursorPosCallback(long windowID, double posX, double posY) {
        this.posX = (float) posX;
        this.posY = (float) posY;

        for (AMouseEvent.Button button : pressedButtons) 
            new MouseDraggedEvent(this.posX, this.posY, button);

        new MouseMovedEvent(this.posX, this.posY);
    }

    public void mouseButtonCallback(long windowID, int buttonID, int action, int mods) {
        AMouseEvent.Button button = translateMouseButton(buttonID);

        if (action == GLFW_PRESS) {
            pressedButtons.add(button);
            new MouseButtonPressedEvent(this.posX, this.posY, button);
        } else if (action == GLFW_RELEASE) {
            pressedButtons.remove(button);
            new MouseButtonReleasedEvent(this.posX, this.posY, button);
        }
    }

    public void scrollCallback(long windowID, double offsetX, double offsetY) {
        new MouseScrolledEvent(this.posX, this.posY, (float) offsetX, (float) offsetY);
    }

    public void keyCallback(long window, int keyID, int scancode, int action, int mods) {
        AKeyEvent.Key key = translateKey(keyID);

        if (action == GLFW_PRESS) {
            new KeyPressedEvent(key);
        } else if (action == GLFW_RELEASE) {
            new KeyReleasedEvent(key);
        }
    }

    private AMouseEvent.Button translateMouseButton(int glfwButtonID) {
        return null;
    }

    private AKeyEvent.Key translateKey(int glfwKeyID) {
        return null;
    }
}