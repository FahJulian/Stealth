package com.github.fahjulian.stealth.ui.property;

public class UIProperty<T>
{
    private final Type<T> type;
    private final T value;

    public UIProperty(Type<T> type, T value)
    {
        this.type = type;
        this.value = value;
    }

    public Type<T> getType()
    {
        return type;
    }

    public T getValue()
    {
        return value;
    }
}

