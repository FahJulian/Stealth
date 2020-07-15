package com.github.fahjulian.stealth.core.resources;

/** A blueprint that is used to compare and create ResourcePool Resources. */
public interface IResourceBlueprint<R extends IResource>
{
    /**
     * Checks if the two blueprints would create similar resources
     * 
     * @param blueprint
     *                      The blueprint to compare with.
     * 
     * @return Whether or not the blueprints are equal.
     */
    boolean equals(IResourceBlueprint<R> blueprint);

    /**
     * The blueprit must implement some method to compare Resources via Strings.
     * 
     * @param key
     *                The String to compare by
     * 
     * @return Whether or not the key fits to the resource
     */
    default boolean equals(String key)
    {
        return getKey().equals(key);
    }

    /**
     * The Blueprint must implement a primary String that is used to compare it to
     * others
     * 
     * @return The Blueprints implementation of this primary key
     */
    String getKey();

    /**
     * Creates a new resource of type R
     * 
     * @return The created resource
     */
    R create();

    /**
     * Get the class of the resource assosiated with this blueprint.
     */
    Class<? extends R> getResourceClass();
}
