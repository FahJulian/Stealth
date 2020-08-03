package com.github.fahjulian.stealth.core.scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.IEventLayer;
import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.events.key.KeyReleasedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseButtonReleasedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseScrolledEvent;

/**
 * A Layer is part of a Scene and can block events from being passed to lower
 * layers. It holds Entities.
 */
public abstract class AbstractLayer<S extends AbstractScene> implements IEventLayer
{
    private final List<Entity> entities;
    private final List<Class<? extends AbstractEvent>> currentlyBlockedEvents;
    private EventDispatcher eventDispatcher;
    private boolean initialized;

    protected S scene;

    public AbstractLayer(S scene)
    {
        this.scene = scene;
        this.entities = new ArrayList<>();
        this.currentlyBlockedEvents = new ArrayList<>();
        this.initialized = false;
    }

    /**
     * This method is called during initialization of the App. It should register
     * Event Listeners and set up Entities.
     */
    abstract protected void onInit();

    public void init(AbstractScene scene)
    {
        if (!(scene == this.scene))
        {
            Log.error("(AbstractLayer) Layers must be added to the same scene they were constructed with.");
            return;
        }

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
        if (!currentlyBlockedEvents.contains(eventClass))
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

    public void add(Collection<Entity> group)
    {
        for (Entity e : group)
            this.add(e);
    }

    public void add(Entity... entities)
    {
        for (Entity e : entities)
            this.add(e);
    }

    public S getScene()
    {
        return scene;
    }

    @Override
    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }

    protected void blockAllInputEventsIf(Supplier<Boolean> condition)
    {
        List<Class<? extends AbstractEvent>> inputEventClasses = new ArrayList<>();
        inputEventClasses.add(KeyPressedEvent.class);
        inputEventClasses.add(KeyReleasedEvent.class);
        inputEventClasses.add(MouseButtonPressedEvent.class);
        inputEventClasses.add(MouseButtonReleasedEvent.class);
        inputEventClasses.add(MouseDraggedEvent.class);
        inputEventClasses.add(MouseMovedEvent.class);
        inputEventClasses.add(MouseScrolledEvent.class);

        for (Class<? extends AbstractEvent> eventClass : inputEventClasses)
        {
            this.registerEventListener(eventClass, (e) ->
            {
                if (condition.get())
                    this.blockEvent(eventClass);
            });
        }
    }
}
