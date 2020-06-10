package com.github.fahjulian.stealth.events.key;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public abstract class AKeyEvent extends AbstractEvent
{

    protected final Key key;

    protected AKeyEvent(Key key)
    {
        this.key = key;
    }

    public Key getKey()
    {
        return key;
    }

    public static enum Key
    {
        SPACE, W, A, S, D, UNKNOWN;
    }
}
