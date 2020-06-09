package com.github.fahjulian.stealth.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.event.AEvent;
import com.github.fahjulian.stealth.core.event.EventManager;
import com.github.fahjulian.stealth.core.scene.ALayer;
import com.github.fahjulian.stealth.core.util.Log;

/**
 * Stealth features a component based entity system. An Entity just holds a
 * name, a transform and a list of components. It can not be subclassed.
 */
public final class Entity
{
    private final String name;
    private final Transform transform;
    private final List<AComponent> components;
    private EventManager eventManager;
    private ALayer layer;
    private boolean initialized, active;

    /**
     * Construct a new Entity with a name, transform and possible initial components
     * 
     * @param name
     *                       The Name of the entity. Mostly for debugging purposes
     * @param transform
     *                       The Transform of the entity.
     * @param components
     *                       Possible initial components of the Entity
     */
    public Entity(String name, Transform transform, AComponent... components)
    {
        this.name = name;
        this.transform = transform;
        this.components = new ArrayList<>();
        this.initialized = false;

        for (AComponent c : components)
        {
            if (hasComponent(c.getClass()))
            {
                Log.warn("(Entity) Cant add two Components of the same type to entity %s",
                        this.name);
                continue;
            }

            this.components.add(c);
            c.setEntity(this);
        }
    }

    /**
     * Retrieve the component of the specified type if the entity has such a
     * component.
     * 
     * @param <C>
     *                           The class of the component
     * @param componentClass
     *                           The class of the component
     * @return The found component or null
     */
    @SuppressWarnings("unchecked")
    public <C extends AComponent> C getComponent(Class<C> componentClass)
    {
        for (AComponent c : components)
            if (componentClass.isAssignableFrom(c.getClass()))
                return (C) c;

        return null;
    }

    /**
     * Add a component to the entity
     * 
     * @param c
     *              The component to add
     */
    public void addComponent(AComponent c)
    {
        if (hasComponent(c.getClass()))
        {
            Log.warn("(Entity) Cant add two Components of type %s to entity %s", c.getClass(),
                    this.name);
            return;
        }

        components.add(c);
        c.setEntity(this);
        if (initialized)
            c.init();
    }

    /**
     * Initialize all components the entity holds Should only be called after
     * setting the {@link #layer}
     */
    public void init()
    {
        if (layer == null)
        {
            Log.warn("(Entity) Entity %s must be assigned to a layer before init().", name);
            return;
        }

        eventManager = new EventManager("EventManager of entity " + toString());
        layer.getEventManager().addListener(AEvent.class, eventManager::dispatch, 0);

        for (AComponent c : components)
            c.init();

        initialized = true;
        active = true;
    }

    /**
     * Remove the component of type type
     * 
     * @param componentClass
     *                           The class of component ot remove
     */
    public void removeComponent(Class<? extends AComponent> componentClass)
    {
        for (AComponent c : components)
            if (componentClass.isAssignableFrom(c.getClass()))
                components.remove(c);
    }

    /**
     * Check if the entiy holds a component of the specified type
     * 
     * @param componentClass
     *                           The class of component to check for
     * @return Whether or not a component of the specified type has been found
     */
    public boolean hasComponent(Class<?> componentClass)
    {
        for (AComponent c : components)
            if (componentClass.isAssignableFrom(c.getClass()))
                return true;

        return false;
    }

    public EventManager getEventManager()
    {
        return eventManager;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setLayer(ALayer layer)
    {
        this.layer = layer;
    }

    public ALayer getLayer()
    {
        return layer;
    }

    public Transform getTransform()
    {
        return transform;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
