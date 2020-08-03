package com.github.fahjulian.stealth.core.entity;

import com.github.fahjulian.stealth.core.resources.ISerializable;

/**
 * A blueprint to create components from. Should be implemented as an inner
 * class by all components
 */
public interface IComponentBlueprint<C extends AbstractComponent> extends ISerializable
{
    /**
     * Create the component with all the given specifications
     * 
     * @return The newly created component
     */
    C createComponent();
}
