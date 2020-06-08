package com.github.fahjulian.stealth.core;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import com.github.fahjulian.stealth.core.event.EventManager;
import com.github.fahjulian.stealth.core.scene.AScene;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.UpdateEvent;

public abstract class AApplication {

    protected Window window;
    private AScene scene;
    private EventManager mainEventManager;
    private boolean running;
    private boolean initialized;
    private String title;
    private int width, height;
    protected boolean debug;
    protected String logDir;

    private static AApplication instance;

    protected AApplication(String title, int width, int height, String logDir, boolean debug) {
        instance = this;

        this.title = title;
        this.width = width;
        this.height = height;
        this.debug = debug;
        this.logDir = logDir;
        this.running = false;
        this.initialized = false;

        init();
    }

    public static AApplication get() {
        return instance;
    }

    abstract protected AScene onInit();

    public void init() {
        Log.init(logDir, debug);
        window = Window.get();
        window.init(title, width, height);
        
        mainEventManager = new EventManager("Main EventManager");

        scene = onInit();
        scene.init();

        initialized = true;
    }

    public void run() {
        running = true;

        final float sPerUpdate = 1.0f / 30.0f;
        float deltaSeconds = 0.0f;
        float dueUpdates = 0.0f;
        
        long startTime = System.nanoTime();
        while (!window.isClosed()) {
            window.pollEvents();

            dueUpdates += deltaSeconds / sPerUpdate;
            while (dueUpdates >= 1) {
                new UpdateEvent(deltaSeconds);
                dueUpdates--;
            }

            glClear(GL_COLOR_BUFFER_BIT);
            new RenderEvent();
            window.swapBuffers();

            long endTime = System.nanoTime();
            deltaSeconds = (endTime - startTime) / 1.0e9f;
            startTime = endTime;
        }

        Window.get().delete();
    }

    public void setScene(AScene scene) {
        this.scene = scene;
        if (initialized) scene.init();
    }

    public AScene getScene() {
        return scene;
    }

    public EventManager getMainEventManager() {
        return mainEventManager;
    }

    public boolean isRunning() {
        return running;
    }
}
