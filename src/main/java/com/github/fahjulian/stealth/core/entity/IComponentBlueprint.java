package com.github.fahjulian.stealth.core.entity;

public interface IComponentBlueprint<C extends AbstractComponent>
{
    C createComponent();
}
