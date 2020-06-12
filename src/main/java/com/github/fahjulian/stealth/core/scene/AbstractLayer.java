package com.github.fahjulian.stealth.core.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.IEventLayer;
import com.github.fahjulian.stealth.core.event.IEventListener;

/**
 * A Layer is part of a Scene and can block events from being passed to lower
 * layers. It holds Entities.
 */
public abstract class AbstractLayer implements IEventLayer
{
    private final List<Entity> entities;
    private final List<Class<? extends AbstractEvent>> currentlyBlockedEvents;
    private EventDispatcher eventDispatcher;
    private boolean initialized;

    protected AbstractScene scene;

    public AbstractLayer()
    {
        this.entities = new ArrayList<>();
        this.currentlyBlockedEvents = new ArrayList<>();
        this.initialized = false;
    }

    /**
     * This method is called during initialization of the App. It should register
     * Event Listeners and set up Entities.
     */
    abstract protected void onInit();

    void init(AbstractScene scene)
    {
        eventDispatcher = new EventDispatcher(scene);
        scene.getEventDispatcher().registerSubLayer(this);
        onInit();

        for (Entity e : entities)
            e.init(this);

        initialized = true;
    }

    /**
     * Registers an Event Listener at the Event Dispatcher
     * 
     * @param <E>
     *                       The class of Event the Listener listens to
     * @param eventClass
     *                       The class of Event the Listener listens to
     * @param listener
     *                       The Event Listener to register
     */
    public <E extends AbstractEvent> void registerEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        eventDispatcher.registerEventListener(eventClass, listener);
    }

    @Override
    public void blockEvent(Class<? extends AbstractEvent> eventClass)
    {
        currentlyBlockedEvents.add(eventClass);
    }

    @Override
    public boolean blocksEvent(Class<? extends AbstractEvent> eventClass)
    {
        for (Class<? extends AbstractEvent> c : currentlyBlockedEvents)
        {
            if (eventClass.equals(c))
            {
                currentlyBlockedEvents.remove(c);
                return true;
            }
        }

        return false;
    }

    /**
     * Adds an Entity to the Layer.
     * 
     * @param entity
     *                   The entity to add
     */
    public void add(Entity entity)
    {
        entities.add(entity);
        if (initialized)
            entity.init(this);
    }

    public AbstractScene getScene()
    {
        return scene;
    }

    @Override
    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }
}
