package com.github.fahjulian.stealth.event.key;

import com.github.fahjulian.stealth.event.AEvent;

public abstract class AKeyEvent extends AEvent {

    public final Key key;

    protected AKeyEvent(Key key) {
        this.key = key;
    }

    public static enum Key {
        UNKNOWN;
    }
}