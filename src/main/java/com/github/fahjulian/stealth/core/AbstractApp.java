package com.github.fahjulian.stealth.core;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.UpdateEvent;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

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
            }

            Renderer2D.startFrame();
            new RenderEvent();
            Renderer2D.endFrame();

            final float endTime = Window.get().getTime();
            deltaSeconds = endTime - startTime;
            startTime = endTime;
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
