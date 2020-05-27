package com.github.fahjulian.stealth;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.event.application.RenderEvent;
import com.github.fahjulian.stealth.event.application.UpdateEvent;

public abstract class AApplication {

    protected static String logDir = ".log/";
    protected static boolean debug = true;

    private Window window;
    private boolean running;

    private static AApplication instance;

    public AApplication(String title, int width, int height) {
        instance = this;
        running = false;

        Log.init(logDir, debug);
        window = Window.get();
        window.init(title, width, height);

        init();
    }

    public static AApplication get() {
        return instance;
    }

    protected abstract void init();

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

            new RenderEvent();
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

    public boolean isRunning() {
        return running;
    }
}
