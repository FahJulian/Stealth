package com.github.fahjulian.stealth.event.key;

public class KeyReleasedEvent extends AKeyEvent {

    public KeyReleasedEvent(Key key) {
        super(key);
    }

    @Override
    public String toString() {
        return String.format("KeyReleasedEvent, key: %s", key.toString());
    }

}
