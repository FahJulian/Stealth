package com.github.fahjulian.stealth.core.event;

/**
 * An Event Layer holds an Event Dispatcher and can block events from being
 * dispatched to lower layers
 */
public interface IEventLayer
{
    /**
     * Checks if the given Event is currently blocked
     * 
     * @param eventClass
     *                       The class of Event to check
     * @return Whether or not this event class is currently being blocked
     */
    boolean blocksEvent(Class<? extends AbstractEvent> eventClass);

    /**
     * Blocks the event from being dispatched to lower layers
     * 
     * @param eventClass
     *                       The type of Event to block
     */
    void blockEvent(Class<? extends AbstractEvent> eventClass);
}
