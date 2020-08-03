package com.github.fahjulian.stealth.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.resources.SerializablePool;

/**
 * An Entity Blueprint holds information about components and can create a new
 * entity from that information
 */
public class EntityBlueprint implements ISerializable
{
    private final IComponentBlueprint<?>[] initialComponents;

    /**
     * Construct a new Enitiy blueprint
     *
     * @param initialComponents
     *                              Blueprints to create the initial components the
     *                              created entities will have
     */
    public EntityBlueprint(IComponentBlueprint<?>... initialComponents)
    {
        this.initialComponents = initialComponents;
    }

    /**
     * Creates a new entity with all the initial components specified in the
     * constructor of this blueprint
     * 
     * @param name
     *                      The name of the new entity
     * @param transform
     *                      The transform of the new entity
     * 
     * @return The new entity
     */
    public Entity create(String name, Transform transform)
    {
        AbstractComponent[] components = new AbstractComponent[initialComponents.length];
        for (int i = 0; i < components.length; i++)
            components[i] = initialComponents[i].createComponent();

        return new Entity(name, transform, components);
    }

    @Override
    public void serialize(Map<String, Object> fields)
    {
        for (int i = 0; i < initialComponents.length; i++)
            fields.put(String.format("component%d", i), initialComponents[i]);
    }

    @Deserializer
    public static EntityBlueprint deserialize(Map<String, String> fields)
    {
        String s = null;
        List<IComponentBlueprint<?>> components = new ArrayList<>();
        for (int i = 0; (s = fields.get(String.format("component%d", i))) != null; i++)
            components.add(SerializablePool.<IComponentBlueprint<?>>deserialize(s));
        return new EntityBlueprint(components.toArray(new IComponentBlueprint<?>[components.size()]));
    }
}
