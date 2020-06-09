package com.github.fahjulian.stealth.core;

import com.github.fahjulian.stealth.core.event.EventManager;
import com.github.fahjulian.stealth.core.scene.AScene;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.UpdateEvent;
import com.github.fahjulian.stealth.graphics.Renderer2D;

public abstract class AApplication
{
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

    protected AApplication(String title, int width, int height, String logDir, boolean debug)
    {
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

    public static AApplication get()
    {
        return instance;
    }

    abstract protected AScene onInit();

    public void init()
    {
        Log.init(logDir, debug);
        window = Window.get();
        window.init(title, width, height);

        mainEventManager = new EventManager("Main EventManager");

        scene = onInit();
        scene.init();

        initialized = true;
    }

    public void run()
    {
        running = true;

        final float sPerUpdate = 1.0f / 60.0f;
        float deltaSeconds = 0.0f;
        float dueUpdates = 0.0f;

        long startTime = System.nanoTime();
        long lastUpdate = startTime;

        while (!window.isClosed())
        {
            window.pollEvents();

            dueUpdates += deltaSeconds / sPerUpdate;
            while (dueUpdates >= 1)
            {
                final float updateDeltaSeconds = (startTime - lastUpdate) / 1.0e9f;
                new UpdateEvent(updateDeltaSeconds);
                lastUpdate = startTime;
                dueUpdates--;
            }

            window.clear();
            new RenderEvent();
            window.swapBuffers();

            final long endTime = System.nanoTime();
            deltaSeconds = (endTime - startTime) / 1.0e9f;
            startTime = endTime;
        }

        Renderer2D.destroy();
        Window.get().destroy();
    }

    public void setScene(AScene scene)
    {
        this.scene = scene;
        if (initialized)
            scene.init();
    }

    public AScene getScene()
    {
        return scene;
    }

    public EventManager getMainEventManager()
    {
        return mainEventManager;
    }

    public boolean isRunning()
    {
        return running;
    }
}
