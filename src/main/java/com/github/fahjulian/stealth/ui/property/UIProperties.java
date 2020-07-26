package com.github.fahjulian.stealth.ui.property;

import java.util.HashMap;
import java.util.Map;

public class UIProperties
{
    private final Map<Type<?>, Object> properties;

    public UIProperties(UIProperty<?>[] defaultProperties, UIProperty<?>... properties)
    {
        this.properties = new HashMap<>();
        for (UIProperty<?> property : defaultProperties)
            this.set(property);
        for (UIProperty<?> property : properties)
            this.set(property);
    }

    public UIProperties(UIProperty<?>... properties)
    {
        this(UIProperties.generateDefault(), properties);
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

    public static UIProperty<?>[] generateDefault()
    {
        UIProperty<?>[] properties = new UIProperty[Types.allTypes.size()];
        for (int i = 0; i < properties.length; i++)
            properties[i] = Types.allTypes.get(i).getDefault();

        return properties;
    }
}
