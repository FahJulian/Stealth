package com.github.fahjulian.stealth.entity;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.stealth.core.AScene;
import com.github.fahjulian.stealth.core.Log;

/**
 * Stealth features a component based entity system.
 * An Entity just holds a name, a transform and a list of components.
 * It can not be subclassed.
 */
public final class Entity {
    
    private final String name;
    private final Transform transform;
    private final Map<IComponentType, AComponent> components;
    private AScene scene;            // TODO: Change to layer??
    private boolean isInitialized;

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
        this.isInitialized = false;

        for (AComponent c : components) {
            if (hasComponent(c.getType())) {
                Log.warn("(Entity) Cant add two Components of the same type to entity %s", this.name);
                continue;
            }
            this.components.put(c.getType(), c);
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
    public <C extends AComponent> C getComponent(IComponentType componentType) throws ClassCastException {
        for (IComponentType type : components.keySet()) {
            if (type == componentType) {
                return (C) components.get(type);
            }
        }

        return null;
    }

    /**
     * Add a component to the entity
     * @param c The component to add
     */
    public void addComponent(AComponent c) {
        if (hasComponent(c.getType())) {
            Log.warn("(Entity) Cant add two Components of the same type to entity %s", this.name);
            return;
        }

        components.put(c.getType(), c);
        if (isInitialized) c.init();
    }

    /**
     * Replace the component of the specified component with the new component
     * @param type The type of component to replace
     * @param c The new component
     */
    public void replaceComponent(IComponentType type, AComponent c) {
        if (!hasComponent(c.getType())) {
            Log.warn("(Entity) Cant replace non-existent component of type %s", type);
            return;
        }

        if (type != c.getType()) {
            Log.warn("(Entity) Invalid ComponentType %s for Component %s", type, c);
            return;
        } 

        components.replace(type, c);
        if (isInitialized) c.init();
    }

    /**
     * Initialize all components the entity holds
     */
    public void init() {
        for (AComponent c : components.values()) {
            c.init();
        }
        isInitialized = true;
    }

    /**
     * Remove the component of type type
     * @param type The type of component ot remove
     */
    public void removeComponent(IComponentType type) {
        this.components.remove(type);
    }

    /**
     * Check if the entiy holds a component of the specified type
     * @param componentType The type of component to check for
     * @return Whether or not a component of the specified type has been found
     */
    public boolean hasComponent(IComponentType componentType) {
        return components.containsKey(componentType);
    }

    public void setScene(AScene scene) {
        this.scene = scene;
    }

    public String getName() {
        return name;
    }

    public AScene getScene() {
        return scene;
    }

    public Transform getTransform() {
        return transform;
    }

    @Override
    public String toString() {
        return name;
    }
}
