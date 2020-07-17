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

    @SuppressWarnings("unchecked")
    public static final <R extends IResource> R getResource(Class<R> resourceClass, String key)
    {
        Map<String, IResource> existingResources = resources.get(resourceClass);
        if (existingResources == null)
            return null;

        return (R) existingResources.get(key);
    }

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
