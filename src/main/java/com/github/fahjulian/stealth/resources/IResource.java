package com.github.fahjulian.stealth.resources;

/** A resource that can be held by the ResourcePool */
public interface IResource
{
    public IResourceBlueprint<? extends IResource> getBlueprint();

    default boolean equals(IResource resource)
    {
        if (resource.getClass().isInstance(this))
            return getBlueprint().equals(resource.getBlueprint());
        else
            return false;
    }
}
