package com.github.fahjulian.stealth.ui.constraint;

public class UIConstraint
{
    private final Type type;
    private final float value;

    public UIConstraint(Type type, float value)
    {
        this.type = type;
        this.value = value;
    }

    public Type getType()
    {
        return type;
    }

    public float getValue()
    {
        return value;
    }
}
