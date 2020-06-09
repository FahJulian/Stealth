package com.github.fahjulian.stealth.core.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.AApplication;
import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.event.AEvent;
import com.github.fahjulian.stealth.core.event.EventManager;
import com.github.fahjulian.stealth.core.event.IEventListener;

public abstract class ALayer
{
    private final List<Entity> entities;
    private int index;
    protected AScene scene;
    private EventManager eventManager;
    private boolean initialized;
    private boolean active;

    public ALayer()
    {
        entities = new ArrayList<>();
        initialized = false;
    }

    abstract protected void onInit();

    /**
     * Initialize the Layer on the given scene
     * 
     * @param scene
     *                  The scene to initialize the sprite on
     * @param index
     *                  The index of the layer in the layerstack of the scene
     */
    public void init(AScene scene, int index)
    {
        this.scene = scene;
        this.index = index;

        eventManager = new EventManager(String.format("EventManager of Layer %d", index));
        AApplication.get().getMainEventManager().addListener(AEvent.class, eventManager::dispatch,
                this::isActive, index);

        onInit();

        for (Entity e : entities)
            e.init();

        initialized = true;
        active = true;
    }

    /**
     * Add an event listener (mostly in a component on the layer)
     * 
     * @param <E>
     *                       The type of Event the listener listens to
     * @param eventClass
     *                       The class of the event
     * @param listener
     *                       The listener to register
     */
    public <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        eventManager.addListener(eventClass, listener, index);
    }

    /**
     * Add an Entity to the Layer
     * 
     * @param e
     *              The Entity to add
     */
    public void add(Entity e)
    {
        entities.add(e);
        e.setLayer(this);
        if (initialized)
            e.init();
    }

    public boolean isActive()
    {
        return active;
    }

    public EventManager getEventManager()
    {
        return eventManager;
    }
}
