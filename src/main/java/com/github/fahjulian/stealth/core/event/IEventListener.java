package com.github.fahjulian.stealth.core.event;

/**
 * An event listener is a function that receives an Event and returs whether or
 * not the event should be viewed as handled afterwards.
 * 
 * @param <E>
 *                The type of event the function listens to
 */
@FunctionalInterface
public interface IEventListener<E extends AEvent>
{
    boolean onEvent(E event);
}
