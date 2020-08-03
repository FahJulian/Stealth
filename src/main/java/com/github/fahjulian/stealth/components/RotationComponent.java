package com.github.fahjulian.stealth.components;

import java.util.Map;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.IComponentBlueprint;
import com.github.fahjulian.stealth.events.application.UpdateEvent;

/** Rotates the entity at a given speed on the z-Axis */
public class RotationComponent extends AbstractComponent
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
    protected void onInit()
    {
        registerEventListener(UpdateEvent.class, this::onUpdate);
    }

    private void onUpdate(UpdateEvent event)
    {
        entity.getTransform().setRotationZ(entity.getTransform().getRotationZ() + speed * event.getDeltaSeconds());
    }

    public static final class Blueprint implements IComponentBlueprint<RotationComponent>
    {
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
            fields.put("speed", speed);
        }

        public static Blueprint deserialize(Map<String, String> fields)
        {
            return new Blueprint(Float.valueOf(fields.get("speed")));
        }

        @Override
        public String getUniqueKey()
        {
            return String.valueOf(speed);
        }
    }
}
