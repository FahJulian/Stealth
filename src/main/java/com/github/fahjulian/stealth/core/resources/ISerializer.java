package com.github.fahjulian.stealth.core.resources;

@FunctionalInterface
public interface ISerializer<T extends ISerializable>
{
    String serialize(T object);
}
