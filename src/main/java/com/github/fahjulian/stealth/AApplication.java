package com.github.fahjulian.stealth;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.event.application.RenderEvent;
import com.github.fahjulian.stealth.event.application.UpdateEvent;
import com.github.fahjulian.stealth.scene.AScene;

public abstract class AApplication {

    
    protected Window window;
    private AScene scene;
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

    protected abstract AScene onInit();

    public void init() {
        Log.init(logDir, debug);
        window = Window.get();
        window.init(title, width, height);

        scene = onInit();
        scene.init();

        initialized = true;
    }

    public void run() {
        running = true;

        final float updatesPerSecond = 30.0f;
        final float nsPerUpdate = 1.0e9f / updatesPerSecond;
        long timer = System.currentTimeMillis();
        long startTime = System.nanoTime();
        int updates = 0, frames = 0;
        float excessUpdates = 0.0f;

        while (!window.isClosed()) {
            window.pollEvents();

            while (excessUpdates >= 1) {
                new UpdateEvent();
                updates++;
                excessUpdates--;
            }

            glClear(GL_COLOR_BUFFER_BIT);
            new RenderEvent();
            glfwSwapBuffers(Window.get().glfwID);
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                window.setTitle(String.format("%s  |  %d UPS, %d FPS", window.getInitialTitle(), updates, frames));
                updates = frames = 0;
                timer += 1000;
            }

            long endTime = System.nanoTime();
            excessUpdates += (endTime - startTime) / nsPerUpdate;
            startTime = endTime;
        }
    }

    public void setScene(AScene scene) {
        this.scene = scene;
    }

    public AScene getScene() {
        return scene;
    }

    public boolean isRunning() {
        return running;
    }
}
