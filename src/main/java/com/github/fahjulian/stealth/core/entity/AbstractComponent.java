package com.github.fahjulian.stealth.core.entity;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.IEventListener;

/**
 * Abstract class subclassed by all entity components
 */
public abstract class AbstractComponent
{
    protected Entity entity;

    /**
     * This method is called during initialization of the game. It should register
     * all event listeners.
     */
    abstract protected void onInit();

    void init(Entity entity)
    {
        this.entity = entity;
        onInit();
    }

    /**
     * Register an Event Listener at the entities Event Dispatcher
     */
    protected <E extends AbstractEvent> void registerEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        entity.getEventDispatcher().registerEventListener(eventClass, listener);
    }

    public Entity getEntity()
    {
        return entity;
    }

    @Override
    public String toString()
    {
        return String.format("Component %s of entity %s", getClass(), entity);
    }
}
