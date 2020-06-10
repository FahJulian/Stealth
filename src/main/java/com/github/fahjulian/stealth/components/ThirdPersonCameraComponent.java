package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.AbstractApp;
import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type;

import org.joml.Vector2f;

/**
 * Makes the camera follow the entity.
 */
public class ThirdPersonCameraComponent extends AbstractComponent
{
    private Vector2f offsetFromEntity;

    @Override
    protected void onInit()
    {
        registerEventListener(EntityTransformEvent.class, this::onEntityTransform);
        offsetFromEntity = new Vector2f(
                (AbstractApp.get().getWindow().getWidth() - entity.getTransform().getScaleX()) / 2.0f,
                (AbstractApp.get().getWindow().getHeight() - entity.getTransform().getScaleY()) / 2.0f);
    }

    private void onEntityTransform(EntityTransformEvent event)
    {
        if (event.getType() == Type.POSITION)
        {
            Camera cam = AbstractApp.get().getCurrentScene().getCamera();
            cam.setPosition(entity.getTransform().getPositionX() - offsetFromEntity.x,
                    entity.getTransform().getPositionY() - offsetFromEntity.y);
        }
    }
}
