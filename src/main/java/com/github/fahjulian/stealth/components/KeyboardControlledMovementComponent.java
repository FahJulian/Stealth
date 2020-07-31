package com.github.fahjulian.stealth.components;

import java.util.Map;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.AbstractComponentBlueprint;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.events.application.UpdateEvent;
import com.github.fahjulian.stealth.events.key.AbstractKeyEvent.Key;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.events.key.KeyReleasedEvent;

import org.joml.Vector2f;

/**
 * Move the entity by WASD inputs
 */
public class KeyboardControlledMovementComponent extends AbstractComponent
{
    public static final class Blueprint extends AbstractComponentBlueprint<KeyboardControlledMovementComponent>
    {
        static
        {
            AbstractComponentBlueprint.register(Blueprint.class, Blueprint::deserialize);
        }

        private final float speed;

        public Blueprint(float speed)
        {
            this.speed = speed;
        }

        @Override
        public KeyboardControlledMovementComponent createComponent()
        {
            return new KeyboardControlledMovementComponent(speed);
        }

        @Override
        public void serialize(Map<String, Object> fields)
        {
            fields.put("speed", String.valueOf(fields));
        }

        public static Blueprint deserialize(Map<String, String> fields)
        {
            return new Blueprint(0.0f);
        }

        @Override
        public String getUniqueKey()
        {
            // TODO Auto-generated method stub
            return null;
        }
    }

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
        registerEventListener(KeyPressedEvent.class, this::onKeyPressed);
        registerEventListener(KeyReleasedEvent.class, this::onKeyReleased);
        registerEventListener(UpdateEvent.class, this::onUpdate);
    }

    private void onUpdate(UpdateEvent event)
    {
        Transform t = entity.getTransform();
        t.setPositionX(t.getPositionX() + velocity.x * event.getDeltaSeconds());
        t.setPositionY(t.getPositionY() + velocity.y * event.getDeltaSeconds());
    }

    private void onKeyPressed(KeyPressedEvent event)
    {
        if (event.getKey() == Key.W)
            velocity.y -= speed;
        else if (event.getKey() == Key.A)
            velocity.x -= speed;
        else if (event.getKey() == Key.S)
            velocity.y += speed;
        else if (event.getKey() == Key.D)
            velocity.x += speed;
    }

    private void onKeyReleased(KeyReleasedEvent event)
    {
        if (event.getKey() == Key.W)
            velocity.y += speed;
        else if (event.getKey() == Key.A)
            velocity.x += speed;
        else if (event.getKey() == Key.S)
            velocity.y -= speed;
        else if (event.getKey() == Key.D)
            velocity.x -= speed;
    }
}
