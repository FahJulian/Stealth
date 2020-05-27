package com.github.fahjulian.stealth.entity;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.scene.ALayer;

/**
 * Stealth features a component based entity system.
 * An Entity just holds a name, a transform and a list of components.
 * It can not be subclassed.
 */
public final class Entity {
    
    private final String name;
    private final Transform transform;
    private final Map<Class<? extends AComponent>, AComponent> components;
    private ALayer layer;        
    private boolean initialized;

    /**
     * Construct a new Entity with a name, transform and possible initial components
     * @param name The Name of the entity. Mostly for debugging purposes
     * @param transform The Transform of the entity.
     * @param components Possible initial components of the Entity
     */
    public Entity(String name, Transform transform, AComponent... components) {
        this.name = name;
        this.transform = transform;
        this.components = new HashMap<>();
        this.initialized = false;

        for (AComponent c : components) {
            if (hasComponent(c.getClass())) {
                Log.warn("(Entity) Cant add two Components of the same type to entity %s", this.name);
                continue;
            }
            
            this.components.put(c.getClass(), c);
            c.setEntity(this);
        }
    }

    /**
     * Retrieve the component of the specified type if the entity has such a component.
     * @param <C> The class of the component. Should only be included if component should be returned casted
     * @param componentType The type of the component
     * @throws ClassCastException If the component can not be casted to C (If the class does not fit to the type)
     * @return The found component or null
     */
    @SuppressWarnings("unchecked")
    public <C extends AComponent> C getComponent(Class<C> componentClass) {
        for (Class<? extends AComponent> key : components.keySet()) {
            if (componentClass.isAssignableFrom(key))
                return (C) components.get(key);
        }

        return null;
    }

    /**
     * Add a component to the entity
     * @param c The component to add
     */
    public void addComponent(AComponent c) {
        if (hasComponent(c.getClass())) {
            Log.warn("(Entity) Cant add two Components of type %s to entity %s", c.getClass(), this.name);
            return;
        }

        components.put(c.getClass(), c);
        c.setEntity(this);
        if (initialized) c.onInit();
    }

    /**
     * Initialize all components the entity holds
     */
    public void init() {
        if (layer == null) {
            Log.warn("(Entity) Entity %s must be assigned to a layer before init().", name);
            return;
        }

        for (AComponent c : components.values()) {
            c.onInit();
        }
        initialized = true;
    }

    /**
     * Remove the component of type type
     * @param type The type of component ot remove
     */
    public <C extends AComponent> void removeComponent(Class<C> componentClass) {
        for (Class<?> key : components.keySet())
            if (componentClass.isAssignableFrom(key))
                components.remove(key);
    }

    /**
     * Check if the entiy holds a component of the specified type
     * @param componentType The type of component to check for
     * @return Whether or not a component of the specified type has been found
     */
    public <C extends AComponent> boolean hasComponent(Class<C> componentClass) {
        for (Class<?> key : components.keySet())
            if (componentClass.isAssignableFrom(key))
                return true;

        return false;
    }

    public void setLayer(ALayer layer) {
        this.layer = layer;
    }

    public String getName() {
        return name;
    }

    public ALayer getLayer() {
        return layer;
    }

    public Transform getTransform() {
        return transform;
    }

    @Override
    public String toString() {
        return name;
    }
}
