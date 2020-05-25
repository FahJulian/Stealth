package com.github.fahjulian.stealth.event;

/**
 * Abstract class subclassed by all events that happen
 * in the app loop, including:
 * Window events (Updating, rendering),
 * Input events (Mouse, Keyboard),
 * Entity events (Collision).
 */
public abstract class AEvent {
    
    boolean handled;

    /**
     * Must be called by subclassed in order for them 
     * to be dispatched to their listeners
     */
    protected void dispatch() {
        EventManager.dispatch(this);
    }

    /**
     * @return The type of the event
     */
    abstract public IEventType getType();
}
