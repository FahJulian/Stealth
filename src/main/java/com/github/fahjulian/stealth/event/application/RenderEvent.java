package com.github.fahjulian.stealth.event.application;

import com.github.fahjulian.stealth.event.AEvent;

public class RenderEvent extends AEvent {

    // TODO: Improve for graphcis system
    public RenderEvent() {
        dispatch();
    }

    @Override
    public String toString() {
        return "RenderEvent";
    }
    
}