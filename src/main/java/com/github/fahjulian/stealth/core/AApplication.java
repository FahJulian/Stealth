package com.github.fahjulian.stealth.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

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

    public void setScene(AScene scene)
    {
        this.scene = scene;
        if (initialized)
            scene.init();
    }

    public Window getWindow()
    {
        return window;
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
