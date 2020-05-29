package com.github.fahjulian.stealth.entity.component;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.Entity;
import com.github.fahjulian.stealth.event.AEvent;
import com.github.fahjulian.stealth.event.IEventListener;

/**
 * Abstract class subclassed by all entity components
 */
public abstract class AComponent {

    protected Entity entity;

    /**
     * Initializes the Component. Sets up event listeners.
     * Should only be called after setting the entity and the entity's layer.
     */
    abstract public void onInit();
    abstract public String toString();

    /**
     * Set the Entity of the component. Should only be done once.
     * @param entity The Entity to pass
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Add an event listener at the apropiate index.
     */
    protected <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        if (entity == null || entity.getLayer() == null) {
            Log.warn("Cant add Event Listener to a component that has not been assigned an entity that is part of a layer.");
            return;
        }

        entity.getLayer().addEventListener(eventClass, listener);
    } 

    public Entity getEntity() {
        return entity;
    }
}
