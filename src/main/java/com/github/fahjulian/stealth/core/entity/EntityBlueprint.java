package com.github.fahjulian.stealth.core.entity;

public class EntityBlueprint
{
    private final String name;
    private final IComponentBlueprint<?>[] initialComponents;

    public EntityBlueprint(String name, IComponentBlueprint<?>... initialComponents)
    {
        this.name = name;
        this.initialComponents = initialComponents;
    }

    public Entity create(Transform transform)
    {
        AbstractComponent[] components = new AbstractComponent[initialComponents.length];
        for (int i = 0; i < components.length; i++)
            components[i] = initialComponents[i].createComponent();

        return new Entity(name, transform, components);
    }
}
