package com.github.fahjulian.stealth.event.application;

import com.github.fahjulian.stealth.event.AEvent;

public class UpdateEvent extends AEvent {

    public final int ID;

    private static int id = 0;

    public UpdateEvent() {
        this.ID = id++;
    }

    @Override
    public String toString() {
        return String.format("UpdateEvent #%d", ID);
    }
    
}