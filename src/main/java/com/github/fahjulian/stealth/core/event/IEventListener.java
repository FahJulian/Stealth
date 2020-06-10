package com.github.fahjulian.stealth.core.event;

/**
 * An Event Listener gets notified if an Event is Dispatched by an Event
 * Dispatcher if it is registered at the Dispatcher.
 */
@FunctionalInterface
public interface IEventListener<E extends AbstractEvent>
{
    void onEvent(E event);
}
