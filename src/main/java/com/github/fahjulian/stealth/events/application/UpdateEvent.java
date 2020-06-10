package com.github.fahjulian.stealth.events.application;

import com.github.fahjulian.stealth.core.event.AbstractEvent;

/** Event thrown every time the app should be updated */
public class UpdateEvent extends AbstractEvent
{
    private final float deltaSeconds;
    private final int updateCount;

    private static int updateCounter = 0;

    public UpdateEvent(float deltaSeconds)
    {
        this.deltaSeconds = deltaSeconds;
        this.updateCount = updateCounter++;
        dispatch();
    }

    public int getUpdateCount()
    {
        return updateCount;
    }

    public float getDeltaSeconds()
    {
        return deltaSeconds;
    }

    @Override
    public String toString()
    {
        return String.format("UpdateEvent #%d", updateCount);
    }

}
