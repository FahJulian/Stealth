package com.github.fahjulian.stealth.core.entity;

/**
 * An Entity Blueprint holds information about components and can create a new
 * entity from that information
 */
public class EntityBlueprint
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
}
