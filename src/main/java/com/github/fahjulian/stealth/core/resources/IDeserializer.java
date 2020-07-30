package com.github.fahjulian.stealth.core.resources;

@FunctionalInterface
public interface IDeserializer<T extends ISerializable>
{
    T deserialize(String xml);
}
