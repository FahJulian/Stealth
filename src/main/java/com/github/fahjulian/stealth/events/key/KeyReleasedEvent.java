package com.github.fahjulian.stealth.events.key;

public class KeyReleasedEvent extends AKeyEvent
{
    public KeyReleasedEvent(Key key)
    {
        super(key);
        dispatch();
    }

    @Override
    public String toString()
    {
        return String.format("KeyReleasedEvent, key: %s", key.toString());
    }

}
