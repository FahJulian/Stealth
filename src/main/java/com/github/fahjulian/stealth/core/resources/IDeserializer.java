package com.github.fahjulian.stealth.core.resources;

import java.util.Map;

@FunctionalInterface
public interface IDeserializer<T extends ISerializable>
{
    T deserialize(Map<String, String> fields);
}
