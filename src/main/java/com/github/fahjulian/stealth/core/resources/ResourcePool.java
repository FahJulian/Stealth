package com.github.fahjulian.stealth.core.resources;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.stealth.core.util.Log;

public final class ResourcePool
{
    private static final Map<Class<? extends IResource>, Map<String, IResource>> resources;

    static
    {
        resources = new HashMap<>();
    }

    /**
     * Searches all existing resources of the given type with the given key
     * 
     * @param <R>
     *                          The type of the searched resource
     * 
     * @param resourceClass
     *                          The type of the searched resource
     * 
     * @param key
     *                          The key of the searched resource (Check
     *                          implementation of IResource to see what the key of
     *                          the type of resource is)
     * 
     * @return The found resource or null if none is found.
     */
    @SuppressWarnings("unchecked")
    public static final <R extends IResource> R getResource(Class<R> resourceClass, String key)
    {
        Map<String, IResource> existingResources = resources.get(resourceClass);
        if (existingResources == null)
            return null;

        return (R) existingResources.get(key);
    }

    /**
     * Checks if there is already a resource with the given resources key. If not,
     * it loads the given resource.
     * 
     * @param <R>
     *                     The type of the resource
     * @param resource
     *                     The resource to get a similar one of or load
     * 
     * @return The similar resource if found or the newly loaded one
     */
    @SuppressWarnings("unchecked")
    public static final <R extends IResource> R getOrLoadResource(R resource)
    {
        Map<String, IResource> existingResources = null;

        for (Map.Entry<Class<? extends IResource>, Map<String, IResource>> entry : resources.entrySet())
        {
            if (entry.getKey().isInstance(resource))
                existingResources = entry.getValue();
        }

        if (existingResources == null)
        {
            existingResources = new HashMap<>();
            resources.put(resource.getClass(), existingResources);
        }

        if (existingResources.containsKey(resource.getKey()))
        {
            return (R) existingResources.get(resource.getKey());
        }
        else
        {
            Log.info("(ResourcePool) Loading resource %s", resource.getKey());

            try
            {
                resource.load();
            }
            catch (Exception e)
            {
                Log.error("(ResourcePool) Error loading resource %s: %s", resource.getKey(), e.getMessage());
            }

            existingResources.put(resource.getKey(), resource);
            return resource;
        }
    }
}
