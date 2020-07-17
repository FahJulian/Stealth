package com.github.fahjulian.stealth.core.resources;

public interface IResource
{
    /**
     * Resources are sorted with keys.
     * 
     * @return The key to find this resource.
     */
    String getKey();

    /**
     * Resources should not require any time-consuming loading during construction
     * of the instance. Instead, all loading should be done in the implementation of
     * this method.
     */
    void load() throws Exception;
}
