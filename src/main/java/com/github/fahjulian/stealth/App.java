package com.github.fahjulian.stealth;

public abstract class App {

    public App() {
        System.out.println("TEST");
    }

    public static App get() {
        return null;
    }

    public boolean isRunning() {
        return false;
    }
}
