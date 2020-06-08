package com.github.fahjulian.stealth.core.entity;

import com.github.fahjulian.stealth.core.event.AEvent;
import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.core.util.Log;

/**
 * Abstract class subclassed by all entity components
 */
public abstract class AComponent {

    protected Entity entity;
    private boolean active;

    /**
     * Initializes the Component. Sets up event listeners.
     * Should only be called after setting the entity and the entity's layer.
     */
    abstract public void onInit();

    /**
     * Set the Entity of the component. Should only be done once.
     * @param entity The Entity to pass
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void init() {
        onInit();
        active = true;
    }

    /**
     * Add an event listener at the apropiate index.
     */
    protected <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        if (entity == null || entity.getLayer() == null) {
            Log.warn("Cant add Event Listener to a component that has not been assigned an entity that is part of a layer.");
            return;
        }

        entity.getEventManager().addListener(eventClass, listener, this::isActive, 0);
    } 

    public boolean isActive() {
        return entity.isActive() && active;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return String.format("Component %s of entity %s", getClass(), entity);
    }
}
