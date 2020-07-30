package com.github.fahjulian.stealth.core.util;

import java.util.HashMap;
import java.util.Map;

public class MultiKeyMap<K1, K2, V>
{
    private final Map<K1, V> map1;
    private final Map<K2, V> map2;

    public MultiKeyMap()
    {
        this.map1 = new HashMap<>();
        this.map2 = new HashMap<>();
    }

    public void put(K1 key1, K2 key2, V value)
    {
        map1.put(key1, value);
        map2.put(key2, value);
    }

    public V getWithPrimaryKey(K1 key)
    {
        return map1.get(key);
    }

    public V getWithSecondaryKey(K2 key)
    {
        return map2.get(key);
    }

    public boolean containsPrimaryKey(K1 key)
    {
        return map1.containsKey(key);
    }

    public boolean containsSecondaryKey(K2 key)
    {
        return map2.containsKey(key);
    }
}
