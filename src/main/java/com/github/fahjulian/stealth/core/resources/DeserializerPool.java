package com.github.fahjulian.stealth.core.resources;

import com.github.fahjulian.stealth.core.util.MultiKeyMap;

public final class DeserializerPool
{
    private static final MultiKeyMap<String, Class<? extends ISerializable>, IDeserializer<?>> deserializers;

    static
    {
        deserializers = new MultiKeyMap<>();
    }

    public static <C extends ISerializable> void register(Class<C> serializableClass, IDeserializer<C> deserializer)
    {
        deserializers.put(serializableClass.toString(), serializableClass, deserializer);
    }

    public static IDeserializer<?> getDeserializer(String key)
    {
        return deserializers.getWithPrimaryKey(key);
    }

    @SuppressWarnings("unchecked")
    public static <C extends ISerializable> C deserialize(String serializableClass, String xml)
            throws ClassCastException
    {
        try
        {
            return (C) deserializers.getWithPrimaryKey(serializableClass).deserialize(xml);
        }
        catch (ClassCastException e)
        {
            throw e;
        }
    }
}
