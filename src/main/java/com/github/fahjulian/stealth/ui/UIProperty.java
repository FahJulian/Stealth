package com.github.fahjulian.stealth.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.graphics.Color;

public class UIProperty<T>
{
    public static class Type<T>
    {
        static final List<Type<?>> allTypes = new ArrayList<>();

        public static final Type<Color> PRIMARY_COLOR = new Type<>(Color.WHITE);
        public static final Type<Color> HOVER_COLOR = new Type<>(Color.WHITE);

        public final T defaultValue;

        private Type(T defaultValue)
        {
            Type.allTypes.add(this);
            this.defaultValue = defaultValue;
        }

        public UIProperty<T> getDefault()
        {
            return new UIProperty<T>(this, defaultValue);
        }
    }

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
