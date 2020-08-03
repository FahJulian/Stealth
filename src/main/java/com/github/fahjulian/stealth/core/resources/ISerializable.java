package com.github.fahjulian.stealth.core.resources;

import java.util.Map;

import com.github.fahjulian.stealth.core.util.Overridable;

public interface ISerializable
{
    void serialize(Map<String, Object> fields);

    @Overridable
    default String getUniqueKey()
    {
        return null;
    }

    @Overridable
    default void load() throws Exception
    {
    }
}
