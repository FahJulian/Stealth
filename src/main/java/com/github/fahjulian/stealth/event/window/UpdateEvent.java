package com.github.fahjulian.stealth.event.window;

import com.github.fahjulian.stealth.event.AEvent;
import com.github.fahjulian.stealth.event.EventType;

/** Testing purposes only for now */
public class UpdateEvent extends AEvent {
    
    public UpdateEvent() {
        this.dispatch();
    }

    @Override
    public EventType getType() {
        return EventType.UPDATE;
    }
}
