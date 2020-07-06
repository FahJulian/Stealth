package com.github.fahjulian.stealth.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.UpdateEvent;
import com.github.fahjulian.stealth.graphics.Renderer2D;

public abstract class AbstractApp
{
    protected Window window;
    private AbstractScene currentScene;
    private boolean initialized;
    private boolean running;

    private static AbstractApp instance;

    protected AbstractApp(String title, int width, int height, String logDir, boolean debug)
    {
        AbstractApp.instance = this;

        Log.init(logDir, debug);
        window = Window.get();
        window.init(title, width, height);

        currentScene = onInit();
        currentScene.init();
        AbstractEvent.setDefaultDispatcher(currentScene.getEventDispatcher());

        Renderer2D.init(currentScene.getCamera());

        initialized = true;
        running = false;

        Log.info("(AbstractApp) Initialization complete.");
    }

    abstract protected AbstractScene onInit();

    public void run()
    {
        running = true;

        final float sPerUpdate = 1.0f / 60.0f;
        float deltaSeconds = 0.0f;
        float dueUpdates = 0.0f;
        int fpsTimer = 0;
        int fps = 0, ups = 0;

        float startTime = 0.0f;
        float lastUpdate = startTime;

        while (!window.isClosed())
        {
            window.pollEvents();

            dueUpdates += deltaSeconds / sPerUpdate;
            while (dueUpdates >= 1)
            {
                final float updateDeltaSeconds = startTime - lastUpdate;
                new UpdateEvent(updateDeltaSeconds);
                lastUpdate = startTime;
                dueUpdates--;
                ups++;
            }

            window.clear();
            Renderer2D.startFrame();
            new RenderEvent();
            Renderer2D.endFrame();
            window.swapBuffers();
            fps++;

            final float endTime = (float) glfwGetTime();
            deltaSeconds = endTime - startTime;
            startTime = endTime;

            if (endTime - fpsTimer > 1)
            {
                fpsTimer++;
                window.setTitle(String.format("%s  |  %d FPS, %d UPS", window.getInitialTitle(), fps, ups));
                fps = ups = 0;
            }
        }

        Renderer2D.destroy();
        Window.get().destroy();
    }

    public static AbstractApp get()
    {
        return instance;
    }

    public void setScene(AbstractScene scene)
    {
        this.currentScene = scene;
        if (initialized)
            scene.init();

        Renderer2D.setCamera(scene.getCamera());
        AbstractEvent.setDefaultDispatcher(scene.getEventDispatcher());
    }

    public Window getWindow()
    {
        return window;
    }

    public AbstractScene getCurrentScene()
    {
        return currentScene;
    }

    public boolean isRunning()
    {
        return running;
    }
}
