package com.github.fahjulian.stealth.ui.property;

import java.util.HashMap;
import java.util.Map;

public class UIProperties
{
    private final Map<Type<?>, Object> properties;

    public UIProperties(UIProperty<?>... properties)
    {
        this.properties = new HashMap<>();

        for (UIProperty<?> property : properties)
            this.set(property);
    }

    public <T> void set(Type<T> type, T value)
    {
        properties.put(type, value);
    }

    public void set(UIProperty<?> property)
    {
        properties.put(property.getType(), property.getValue());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Type<T> type)
    {
        return (T) properties.get(type);
    }
}
