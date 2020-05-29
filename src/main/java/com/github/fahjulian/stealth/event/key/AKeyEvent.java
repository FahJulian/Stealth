package com.github.fahjulian.stealth.event.key;

import com.github.fahjulian.stealth.event.AEvent;

public abstract class AKeyEvent extends AEvent {

    protected final Key key;

    protected AKeyEvent(Key key) {
        this.key = key;
    }

    public Key getKey() {
        return key;
    }

    public static enum Key {
        SPACE,
        UNKNOWN;
    }
}