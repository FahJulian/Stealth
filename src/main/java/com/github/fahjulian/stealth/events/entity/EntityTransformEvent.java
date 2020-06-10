package com.github.fahjulian.stealth.events.entity;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.event.AbstractEvent;

public class EntityTransformEvent extends AbstractEvent
{
    public static enum Type
    {
        POSITION, SCALE, ROTATION;
    }

    private final Type type;

    public EntityTransformEvent(Type type, Entity entity)
    {
        this.type = type;
        this.dispatcher = entity.getEventDispatcher();
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
