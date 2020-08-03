package com.github.fahjulian.stealth.events.key;

public class KeyPressedEvent extends AbstractKeyEvent
{
    public KeyPressedEvent(Key key)
    {
        super(key);
        super.dispatch();
    }

    @Override
    public String toString()
    {
        return String.format("KeyPressedEvent, key: %s", key.toString());
    }

}
