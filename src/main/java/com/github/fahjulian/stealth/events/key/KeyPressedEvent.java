package com.github.fahjulian.stealth.events.key;

public class KeyPressedEvent extends AKeyEvent {

    public KeyPressedEvent(Key key) {
        super(key);
        dispatch();
    }

    @Override
    public String toString() {
        return String.format("KeyPressedEvent, key: %s", key.toString());
    }
    
}