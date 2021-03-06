package com.github.fahjulian.stealth.core.event;

/**
 * Abstract class subclassed by all Events
 */
public abstract class AbstractEvent
{
    private static EventDispatcher defaultDispatcher;

    /**
     * The dispatcher to pass the event to. Can be modified by subclasses during
     * construction.
     */
    protected EventDispatcher dispatcher;

    protected AbstractEvent()
    {
        this.dispatcher = defaultDispatcher;
    }

    /**
     * Sets the Event Dispatcher all Events get dispatched with by default.
     * 
     * @param defaultDispatcher
     *                              The new default Event Dispatcher
     */
    public static void setDefaultDispatcher(EventDispatcher defaultDispatcher)
    {
        AbstractEvent.defaultDispatcher = defaultDispatcher;
    }

    /**
     * Dispatch the event using the set EventDispatcher
     */
    protected void dispatch()
    {
        if (dispatcher != null)
            dispatcher.dispatch(this);
    }
}
