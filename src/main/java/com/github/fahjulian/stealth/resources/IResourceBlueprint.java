package com.github.fahjulian.stealth.resources;

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
