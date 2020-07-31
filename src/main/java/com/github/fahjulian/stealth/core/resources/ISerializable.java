package com.github.fahjulian.stealth.core.resources;

import java.util.Map;

public interface ISerializable
{
    void serialize(Map<String, Object> fields);

    String getUniqueKey();
}
