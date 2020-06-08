package com.github.fahjulian.stealth.core.event;

import com.github.fahjulian.stealth.core.AApplication;

/**
 * Abstract class subclassed by all events that happen
 * in the app loop, including:
 * Window events (Updating, rendering),
 * Input events (Mouse, Keyboard),
 * Entity events (Collision).
 */
public abstract class AEvent {
    
    protected EventManager manager;
    boolean handled = false;

    protected AEvent() {
        manager = AApplication.get().getMainEventManager();
    }

    /**
     * Must be called by subclassed in order for them 
     * to be dispatched to their listeners
     */
    protected void dispatch() {
        manager.dispatch(this);
    }

    abstract public String toString();
}
