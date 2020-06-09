package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.AApplication;
import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type;

import org.joml.Vector2f;

public class ThirdPersonCameraComponent extends AComponent
{
    private Vector2f offsetFromEntity;

    @Override
    protected void onInit()
    {
        addEventListener(EntityTransformEvent.class, this::onEntityTransform);
        offsetFromEntity = new Vector2f(
                (AApplication.get().getWindow().getWidth() - entity.getTransform().getScaleX()) / 2.0f,
                (AApplication.get().getWindow().getHeight() - entity.getTransform().getScaleY()) / 2.0f);
    }

    private boolean onEntityTransform(EntityTransformEvent event)
    {
        if (event.getType() == Type.POSITION)
        {
            Camera cam = AApplication.get().getScene().getCamera();
            cam.setPosition(entity.getTransform().getPositionX() - offsetFromEntity.x,
                    entity.getTransform().getPositionY() - offsetFromEntity.y);
        }
        return false;
    }
}
