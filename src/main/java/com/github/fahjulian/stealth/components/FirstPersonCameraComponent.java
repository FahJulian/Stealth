package com.github.fahjulian.stealth.components;

import static com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type.POSITION;

import java.util.Map;

import com.github.fahjulian.stealth.core.AbstractApp;
import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.AbstractComponentBlueprint;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent;

import org.joml.Vector2f;

/**
 * Makes the camera follow the entity.
 */
public class FirstPersonCameraComponent extends AbstractComponent
{
    public static final class Blueprint extends AbstractComponentBlueprint<FirstPersonCameraComponent>
    {
        static
        {
            AbstractComponentBlueprint.register(Blueprint.class, (s) -> new Blueprint());
        }

        @Override
        public FirstPersonCameraComponent createComponent()
        {
            return new FirstPersonCameraComponent();
        }

        @Override
        public void serialize(Map<String, Object> fields)
        {
        }

        @Override
        public String getUniqueKey()
        {
            // TODO Auto-generated method stub
            return null;
        }
    }

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
        if (event.getType() == POSITION)
        {
            Camera cam = AbstractApp.get().getCurrentScene().getCamera();
            cam.setPosition(entity.getTransform().getPositionX() - offsetFromEntity.x,
                    entity.getTransform().getPositionY() - offsetFromEntity.y);
        }
    }
}
