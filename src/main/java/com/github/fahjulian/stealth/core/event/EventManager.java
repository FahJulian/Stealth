package com.github.fahjulian.stealth.core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton for registering Event listeners and dispatching events to them.
 */
public final class EventManager
{
    /**
     * Utility class to make EventListeners comparable
     */
    private static class SortedEventListener<E extends AEvent>
            implements IEventListener<E>, Comparable<SortedEventListener<?>>
    {

        private final IEventListener<E> listener;
        private final IEventListenerCondition condition;
        private final int index;

        private SortedEventListener(IEventListener<E> listener, IEventListenerCondition condition, int index)
        {
            this.listener = listener;
            this.condition = condition;
            this.index = index;
        }

        @Override
        public boolean onEvent(E event)
        {
            return condition.shouldEventListenerBeCalled() ? listener.onEvent(event) : false;
        }

        @Override
        public int compareTo(SortedEventListener<?> o)
        {
            return Integer.compare(o.index, this.index);
        }
    }

    private final String name;
    private final Map<Class<? extends AEvent>, List<SortedEventListener<?>>> listeners;
    private final List<SortedEventListener<AEvent>> subManagers;

    public EventManager(String name)
    {
        this.name = name;
        listeners = new HashMap<>();
        subManagers = new ArrayList<>();
    }

    /**
     * Dispatch an event to all of the event listners registered for its type
     * 
     * @param <E>
     *                  The Class of the Event
     * @param event
     *                  The event to dispatch
     */
    @SuppressWarnings("unchecked")
    public <E extends AEvent> boolean dispatch(E event)
    {
        for (Class<?> key : listeners.keySet())
        {
            if (key.isInstance(event))
            {

                for (SortedEventListener<?> listener : listeners.get(key))
                {
                    event.handled = ((IEventListener<E>) listener).onEvent(event);
                    if (event.handled)
                        return true;
                }
            }
        }

        for (SortedEventListener<AEvent> subManager : subManagers)
        {
            event.handled = subManager.onEvent(event);
            if (event.handled)
                return true;
        }

        return false;
    }

    /**
     * Register an EventListener for its type of Event
     * 
     * @param <E>
     *                       The class of the EventListener's event
     * @param eventClass
     *                       The class of the Event the eventlistener listens to
     * @param listener
     *                       The EventListener to register
     * @param index
     *                       The "importance" of the listener (Like a zIndex in
     *                       rendering)
     */
    public <E extends AEvent> void addListener(Class<E> eventClass, IEventListener<E> listener, int index)
    {
        addListener(eventClass, listener, () -> true, index);
    }

    /**
     * Register an EventListener for its type of Event with a condition for it
     * getting called
     * 
     * @param <E>
     *                       The class of the EventListener's event
     * @param eventClass
     *                       The class of the Event the eventlistener listens to
     * @param listener
     *                       The EventListener to register
     * @param condition
     *                       A Supplier for Boolean that checks whether or not the
     *                       EventListener should be called
     * @param index
     *                       The "importance" of the listener (Like a zIndex in
     *                       rendering)
     */
    @SuppressWarnings("unchecked")
    public <E extends AEvent> void addListener(Class<E> eventClass, IEventListener<E> listener,
            IEventListenerCondition condition, int index)
    {
        if (eventClass.equals(AEvent.class))
        {
            subManagers.add((SortedEventListener<AEvent>) new SortedEventListener<E>(listener, condition, index));
            Collections.sort(subManagers);
            return;
        }

        if (!listeners.containsKey(eventClass))
            listeners.put(eventClass, new ArrayList<>());

        listeners.get(eventClass).add(new SortedEventListener<E>(listener, condition, index));
        Collections.sort(listeners.get(eventClass));
    }

    @Override
    public String toString()
    {
        return name;
    }
}
