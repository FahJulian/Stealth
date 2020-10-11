package com.github.fahjulian.stealth.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher<E extends Event> {

    private static int idCounter;
    private final Map<Integer, IEventListener<E>> listeners;
    private final Map<Object, List<Integer>> clientIDs;

    public EventDispatcher() {
        listeners = new HashMap<>();
        clientIDs = new HashMap<>();
    }

    public int add(IEventListener<E> listener) {
        int id = idCounter++;
        listeners.put(id, listener);
        return id;
    }

    public void add(Object client, IEventListener<E> listener) {
        if (clientIDs.get(client) == null)
            clientIDs.put(client, new ArrayList<>());
        clientIDs.get(client).add(add(listener));
    }

    public void remove(int id) {
        listeners.remove(id);
    }

    public void dispatch(E event) {
        for (IEventListener<E> listener : listeners.values())
            listener.onEvent(event);
    }

    public void autoRemoveAll(Object client) {
        for (Integer id : clientIDs.get(client)) {
            remove(id);
        }
    }
}
