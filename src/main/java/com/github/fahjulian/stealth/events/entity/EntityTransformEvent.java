package com.github.fahjulian.stealth.events.entity;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.event.AEvent;

public class EntityTransformEvent extends AEvent
{
    public static enum Type
    {
        POSITION, SCALE;
    }

    private final Type type;

    public EntityTransformEvent(Type type, Entity entity)
    {
        this.type = type;
        this.manager = entity.getEventManager();
        dispatch();
    }

    public Type getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        return "Entity Transform event of Type " + type.toString();
    }
}
