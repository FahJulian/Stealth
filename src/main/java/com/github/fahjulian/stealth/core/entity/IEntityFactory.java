package com.github.fahjulian.stealth.core.entity;

/**
 * A function that construct an Entity with a given transform.
 */
@FunctionalInterface
public interface IEntityFactory
{
    /**
     * Construct the Entity with the transform
     * 
     * @param t
     *              The transform to intitialize the Entity with
     * @return The created entity
     */
    Entity create(Transform t);
}
