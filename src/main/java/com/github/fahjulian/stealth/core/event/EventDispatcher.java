package com.github.fahjulian.stealth.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * An Event Dispatcher holds Event Listeners and other Event Dispatchers and
 * passes Events to them.
 */
public final class EventDispatcher
{
    private final Map<Class<? extends AbstractEvent>, List<IEventListener<? extends AbstractEvent>>> listeners;
    private final List<EventDispatcher> subDispatchers;
    private final SortedSet<IEventLayer> subLayers;

    /**
     * Construct an Event Dispatcher with the given Layerstack. The Layerstack is
     * used to sort the Dispatchers Sub-Layers to correctly dispatch Events if a
     * Layer blocks them
     * 
     * @param layerStack
     *                       The Layerstack to use to sort Sub-Layers. If this Event
     *                       Dispatcher will not hold any Sub-Layers, set this to
     *                       null.
     */
    public EventDispatcher(IEventLayerStack layerStack)
    {
        listeners = new HashMap<>();
        subDispatchers = new ArrayList<>();
        subLayers = new TreeSet<>(layerStack);
    }

    final void dispatch(AbstractEvent event)
    {
        this.dispatchToListeners(event);

        for (EventDispatcher subDispatcher : subDispatchers)
            subDispatcher.dispatch(event);

        for (IEventLayer subLayer : subLayers)
        {
            subLayer.getEventDispatcher().dispatch(event);
            if (subLayer.blocksEvent(event.getClass()))
                return;
        }
    }

    @SuppressWarnings("unchecked")
    private final <E extends AbstractEvent> void dispatchToListeners(E event)
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
     * Register another Event Dispatcher that is not the Dispatcher of an Event
     * Layer. To register an Event Dispatcher that is part of an Event Layer, use
     * {@link #registerSubLayer(IEventLayer)}.
     * 
     * @param dispatcher
     *                       The Dispatcher to register
     */
    public final void registerSubDispatcher(EventDispatcher dispatcher)
    {
        subDispatchers.add(dispatcher);
    }

    /**
     * Register an Event Layer (Its Event Dispatcher) that is part of this
     * Dispatchers Event Layer. To register an Event Dispatcher that is not part of
     * an Event Layer, use {@link #registerSubDispatcher(EventDispatcher)}.
     * 
     * @param layer
     *                  The Sub Event Layer to register
     */
    public final void registerSubLayer(IEventLayer layer)
    {
        subLayers.add(layer);
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
