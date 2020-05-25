package com.github.fahjulian.stealth.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.fahjulian.stealth.core.Log;

/**
 * Singleton for registering Event listeners and dispatching events to them.
 */
public final class EventManager {

    /**
     * Utility class to make EventListeners comparable
     */
    private static class SortedEventListener<E extends AEvent> 
                        implements Comparable<SortedEventListener<E>> {

        private final IEventListener<E> listener;
        private final int index;

        private SortedEventListener(IEventListener<E> listener, int index) {
            this.listener = listener;
            this.index = index;    
        }

        @Override
        public int compareTo(SortedEventListener<E> o) {
            return Integer.compare(o.index, this.index);
        }
    }

    private Map<IEventType, List<SortedEventListener<? extends AEvent>>> listeners;

    private static EventManager instance;

    private EventManager() {
        listeners = new HashMap<>();
    }

    /**
     * Get the singleton instance of the EventManager
     * @return The instance of EventManager
     */
    public static EventManager get() {
        if (instance == null) {
            instance = new EventManager();
            Log.info("(EventManager) Initialized EventManager.");
        }

        return instance;
    }

    /**
     * Dispatch an event to all of the event listners registered for its type
     * @param <E> The Class of the Event
     * @param event The event to dispatch
     */
    @SuppressWarnings("unchecked")
    public static <E extends AEvent> void dispatch(E event) {
        if (get().listeners.containsKey(event.getType())) {
            List<SortedEventListener<? extends AEvent>> listeners = get().listeners.get(event.getType());

            for (int i = 0; !event.handled && i < listeners.size(); i++) {
                IEventListener<E> listener = (IEventListener<E>) listeners.get(i).listener;
                event.handled = listener.onEvent(event);
            }
        }
    }

    /**
     * Register an EventListener for its type of Event
     * @param <E> The class of the EventListener's event
     * @param eventType The type of Event the eventlistener listens to
     * @param listener The EventListener to register
     * @param index The "importance" of the listener (Like a zIndex in rendering)
     */
    public static <E extends AEvent> void addListener(IEventType eventType, IEventListener<E> listener, int index) {
        if (!get().listeners.containsKey(eventType))
            get().listeners.put(eventType, new ArrayList<>());

        get().listeners.get(eventType).add(new SortedEventListener<>(listener, index));
        Collections.sort(get().listeners.get(eventType));
    }
}
