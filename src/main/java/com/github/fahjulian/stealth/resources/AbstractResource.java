package com.github.fahjulian.stealth.resources;

public abstract class AbstractResource
{
    private final IResourceBlueprint<?> blueprint;

    protected AbstractResource(IResourceBlueprint<?> blueprint)
    {
        this.blueprint = blueprint;
    }

    public IResourceBlueprint<?> getBlueprint()
    {
        return blueprint;
    }
}
