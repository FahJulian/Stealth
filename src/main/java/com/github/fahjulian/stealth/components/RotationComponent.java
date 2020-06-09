package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.application.UpdateEvent;

public class RotationComponent extends AComponent
{
    private final float speed;

    /**
     * @param speed
     *                  The rotation speed in degrees per second
     */
    public RotationComponent(float speed)
    {
        this.speed = speed;
    }

    @Override
    public void onInit()
    {
        addEventListener(UpdateEvent.class, this::onUpdate);
    }

    public boolean onUpdate(UpdateEvent event)
    {
        Log.debug("" + event.getDeltaSeconds());
        entity.getTransform().setRotationZ(
                entity.getTransform().getRotationZ() + speed * event.getDeltaSeconds());
        return false;
    }
}
