package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.UpdateEvent;
import com.github.fahjulian.stealth.events.key.AKeyEvent.Key;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.events.key.KeyReleasedEvent;

import org.joml.Vector2f;

public class KeyboardControlledMovementComponent extends AComponent
{
    private final float speed;
    private Vector2f velocity;

    /**
     * @param speed
     *                  The speed of the entity in pixels per second
     */
    public KeyboardControlledMovementComponent(float speed)
    {
        this.speed = speed;
        this.velocity = new Vector2f();
    }

    @Override
    protected void onInit()
    {
        addEventListener(KeyPressedEvent.class, this::onKeyPressed);
        addEventListener(KeyReleasedEvent.class, this::onKeyReleased);
        addEventListener(UpdateEvent.class, this::onUpdate);
    }

    private boolean onUpdate(UpdateEvent event)
    {
        Transform t = entity.getTransform();
        t.setPositionX(t.getPositionX() + velocity.x * event.getDeltaSeconds());
        t.setPositionY(t.getPositionY() + velocity.y * event.getDeltaSeconds());

        return false;
    }

    private boolean onKeyPressed(KeyPressedEvent event)
    {
        if (event.getKey() == Key.W)
            velocity.y += speed;
        else if (event.getKey() == Key.A)
            velocity.x -= speed;
        else if (event.getKey() == Key.S)
            velocity.y -= speed;
        else if (event.getKey() == Key.D)
            velocity.x += speed;

        return false;
    }

    private boolean onKeyReleased(KeyReleasedEvent event)
    {
        if (event.getKey() == Key.W)
            velocity.y -= speed;
        else if (event.getKey() == Key.A)
            velocity.x += speed;
        else if (event.getKey() == Key.S)
            velocity.y += speed;
        else if (event.getKey() == Key.D)
            velocity.x -= speed;

        return false;
    }
}
