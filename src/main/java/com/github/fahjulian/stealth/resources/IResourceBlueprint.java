package com.github.fahjulian.stealth.resources;

public interface IResourceBlueprint<R extends AbstractResource> extends Comparable<IResourceBlueprint<R>>
{
    R create();
}
