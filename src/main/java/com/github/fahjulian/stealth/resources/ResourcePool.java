package com.github.fahjulian.stealth.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.fahjulian.stealth.core.util.Log;

/** Holds all resources that are currently loaded in the game. */
public class ResourcePool
{
    private static final Map<Class<? extends IResource>, List<? extends IResource>> resources;

    static
    {
        resources = new HashMap<>();
    }

    /**
     * Retrieves a resource that fits to the given blueprint or creates a new one
     * from the blueprint if it is not loaded yet.
     * 
     * @param <R>
     *                      The class of the retrieved resource
     * @param blueprint
     *                      The blueprint used for checking for existing resources
     *                      and creating the new one
     * 
     * @return The found/created resource
     */
    @SuppressWarnings("unchecked")
    public static final <R extends IResource> R getOrCreateResource(IResourceBlueprint<R> blueprint)
    {
        List<R> resources = null;
        if (!ResourcePool.resources.containsKey(blueprint.getResourceClass()))
        {
            resources = new ArrayList<>();
            ResourcePool.resources.put(blueprint.getResourceClass(), resources);
        }
        else
        {
            resources = (List<R>) ResourcePool.resources.get(blueprint.getResourceClass());
        }

        for (R resource : resources)
            if (resource.getBlueprint().equals(blueprint))
                return resource;

        // Not existing -> Create new
        R newResource = blueprint.create();
        resources.add(newResource);
        Log.info("(ResourcePool) Loaded resource of type %s: %s", blueprint.getResourceClass(), newResource);
        return newResource;
    }
}
