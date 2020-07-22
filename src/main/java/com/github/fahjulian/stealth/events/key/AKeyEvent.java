package com.github.fahjulian.stealth.events.key;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

public abstract class AKeyEvent extends AbstractEvent
{
    private static int keyIDIterator;

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
        SPACE, CONTROL, W, A, S, D, UNKNOWN;

        public final int ID;

        private Key()
        {
            this.ID = AKeyEvent.keyIDIterator++;
        }

        public static int getKeyAmount()
        {
            return keyIDIterator;
        }
    }
}
