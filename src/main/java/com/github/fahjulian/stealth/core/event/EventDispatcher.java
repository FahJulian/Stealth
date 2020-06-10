package com.github.fahjulian.stealth.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An Event Dispatcher holds Event Listeners and other Event Dispatcher and
 * passes Events to them.
 */
public final class EventDispatcher
{
    private final Map<Class<? extends AbstractEvent>, List<IEventListener<? extends AbstractEvent>>> listeners;
    private final SortedMap<IEventLayer, List<EventDispatcher>> subDispatchers;

    /**
     * Construct an Event Dispatcher with the given Layerstack. The Layerstack is
     * used to sort the Dispatchers Layers to correctly dispatch Events if a Layer
     * blocks them
     * 
     * @param layerStack
     */
    public EventDispatcher(IEventLayerStack layerStack)
    {
        listeners = new HashMap<>();
        subDispatchers = new TreeMap<>(layerStack);
    }

    final boolean dispatch(AbstractEvent event)
    {
        this.dispatchToListeners(event);

        for (IEventLayer layer : subDispatchers.keySet())
        {
            for (EventDispatcher dispatcher : subDispatchers.get(layer))
                if (dispatcher.dispatch(event))
                    return true;

            if (layer.blocksEvent(event.getClass()))
                return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    final <E extends AbstractEvent> void dispatchToListeners(E event)
    {
        for (Class<? extends AbstractEvent> eventClass : listeners.keySet())
        {
            if (eventClass.isInstance(event))
            {
                for (IEventListener<? extends AbstractEvent> listener : listeners.get(eventClass))
                {
                    ((IEventListener<E>) listener).onEvent(event);
                }
            }
        }
    }

    /**
     * Register another Event Dispatcher this Dispatcher will notify when an Event
     * is dispatched.
     * 
     * @param layer
     *                       The Layer the added EventDispatcher is part of. Must be
     *                       part of this Dispatchers specified Layer Stack
     * @param dispatcher
     *                       The Dispatcher to register
     */
    public final void registerSubDispatcher(IEventLayer layer, EventDispatcher dispatcher)
    {
        if (!subDispatchers.containsKey(layer))
            subDispatchers.put(layer, new ArrayList<>());

        subDispatchers.get(layer).add(dispatcher);
    }

    /**
     * Register an Event Listener the Event Dispatcher will notify when an Event is
     * dispatched.
     * 
     * @param <E>
     *                       The class of Event the Listener listens to
     * @param eventClass
     *                       The class of Event the Listener listens to
     * @param listener
     *                       The Event Listener to register
     */
    public final <E extends AbstractEvent> void registerEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        if (!listeners.containsKey(eventClass))
            listeners.put(eventClass, new ArrayList<>());

        listeners.get(eventClass).add(listener);
    }
}
