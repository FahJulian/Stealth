package com.github.fahjulian.stealth.components;

import java.util.Map;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.AbstractComponentBlueprint;
import com.github.fahjulian.stealth.events.application.UpdateEvent;

/** Rotates the entity at a given speed on the z-Axis */
public class RotationComponent extends AbstractComponent
{
    public static final class Blueprint extends AbstractComponentBlueprint<RotationComponent>
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
        public RotationComponent createComponent()
        {
            return new RotationComponent(speed);
        }

        @Override
        public void serialize(Map<String, Object> fields)
        {
            fields.put("speed", String.valueOf(speed));
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

    /**
     * @param speed
     *                  The rotation speed in degrees per second
     */
    public RotationComponent(float speed)
    {
        this.speed = speed;
    }

    @Override
    protected void onInit()
    {
        registerEventListener(UpdateEvent.class, this::onUpdate);
    }

    private void onUpdate(UpdateEvent event)
    {
        entity.getTransform().setRotationZ(entity.getTransform().getRotationZ() + speed * event.getDeltaSeconds());
    }
}
