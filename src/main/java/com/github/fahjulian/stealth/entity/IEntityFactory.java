package com.github.fahjulian.stealth.entity;

@FunctionalInterface
public interface IEntityFactory {
    Entity make(Transform t);    
}
