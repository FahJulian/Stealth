package com.github.fahjulian.stealth.core.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;

/**
 * Stealth features a component based entity system. An Entity just holds a
 * name, a transform and a list of components. It can not be subclassed.
 */
public final class Entity
{
    private final String name;
    private final Transform transform;
    private final List<AbstractComponent> components;
    private EventDispatcher eventDispatcher;
    private AbstractLayer layer;
    private boolean initialized;

    /**
     * Construct a new Entity with a name for debugging, a Transform and optional
     * initial components
     * 
     * @param name
     *                       The Name of the entity. Mostly for debugging purposes
     * @param transform
     *                       The Transform of the entity.
     * @param components
     *                       Optional initial components of the Entity
     */
    public Entity(String name, Transform transform, AbstractComponent... components)
    {
        this.name = name;
        this.transform = transform;
        this.components = new ArrayList<>();
        this.initialized = false;

        this.transform.setEntity(this);

        for (AbstractComponent c : components)
            addComponent(c);
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
    public <C extends AbstractComponent> C getComponent(Class<C> componentClass)
    {
        for (AbstractComponent c : components)
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
    public void addComponent(AbstractComponent c)
    {
        components.add(c);
        if (initialized)
            c.init(this);
    }

    /**
     * Initialize the Entity and all components it holds.
     * 
     * @param layer
     *                  The Layer to initialize the Entity on.
     */
    public void init(AbstractLayer layer)
    {
        this.layer = layer;

        eventDispatcher = new EventDispatcher(layer.getScene());
        layer.getEventDispatcher().registerSubDispatcher(layer, eventDispatcher);

        for (AbstractComponent c : components)
            c.init(this);

        initialized = true;
    }

    /**
     * Remove the component of the given class from the entity
     * 
     * @param componentClass
     *                           The class of the component ot remove
     */
    public void removeComponent(Class<? extends AbstractComponent> componentClass)
    {
        for (AbstractComponent c : components)
            if (componentClass.isInstance(c))
                components.remove(c);
    }

    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }

    public AbstractLayer getLayer()
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
