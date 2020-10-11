package com.github.fahjulian.stealth.core;

import static org.lwjgl.opengl.GL11.glFlush;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.window.Window;

public abstract class App {

    private static App instance;

    protected App(String title, int width, int height, String logDir) {
        assert App.instance == null : Log.coreError("App", "Can't create more than one App.");
        App.instance = this;
        Log.init(logDir);
        Window.init(title, width, height);
        Window.setVisible(true);
        Log.coreInfo("AbstractApp", "Initialization complete.");
    }

    public synchronized void run() {
        final float secondsPerUpdate = 1.0f / 60.0f;
        float delta = 0.0f;
        float dueUpdates = 0.0f;
        float startTime = 0.0f;
        float lastUpdate = startTime;

        while (!Window.isClosed()) {
            Window.pollEvents();

            dueUpdates += delta / secondsPerUpdate;
            while (dueUpdates >= 1.0f) {
                final float updateDelta = startTime - lastUpdate;
                // TODO: Create update event / update the app
                lastUpdate = startTime;
                dueUpdates--;
            }

            Window.clear();
            glFlush();
            Window.swapBuffers();

            // TODO: Render

            final float endTime = Window.getTime();
            delta = endTime - startTime;
            startTime = endTime;
        }
    }
}
