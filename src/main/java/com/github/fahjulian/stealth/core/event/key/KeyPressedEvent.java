package com.github.fahjulian.stealth.core.event.key;

import com.github.fahjulian.stealth.core.event.Event;
import com.github.fahjulian.stealth.core.window.Key;

public class KeyPressedEvent extends Event {

    public final Key key;

    public KeyPressedEvent(Key key) {
        this.key = key;
    }
}
