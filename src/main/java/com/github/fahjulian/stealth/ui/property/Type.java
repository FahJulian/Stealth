package com.github.fahjulian.stealth.ui.property;

public class Type<T>
{
    public final T defaultValue;

    Type(T defaultValue)
    {
        Types.allTypes.add(this);
        this.defaultValue = defaultValue;
    }

    UIProperty<T> getDefault()
    {
        return new UIProperty<T>(this, defaultValue);
    }
}
