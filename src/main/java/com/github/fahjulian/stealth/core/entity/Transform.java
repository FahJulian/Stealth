package com.github.fahjulian.stealth.core.entity;

import static com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type.POSITION;
import static com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type.ROTATION;
import static com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type.SCALE;

import java.util.Map;

import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * A Transform is a data set that holds information about an entities position
 * and scale.
 */
public final class Transform implements ISerializable
{
    private Entity entity;
    private final Vector3f position;
    private final Vector3f scale;
    private final Vector3f rotation;

    public Transform()
    {
        this(new Vector3f(), new Vector3f(), new Vector3f());
    }

    public Transform(Vector2f position)
    {
        this(new Vector3f(position, 0.0f), new Vector3f(), new Vector3f());
    }

    public Transform(Vector3f position)
    {
        this(position, new Vector3f(), new Vector3f());
    }

    public Transform(float posX, float posY)
    {
        this(new Vector3f(posX, posY, 0.0f), new Vector3f(), new Vector3f());
    }

    public Transform(float posX, float posY, float posZ)
    {
        this(new Vector3f(posX, posY, posZ), new Vector3f());
    }

    public Transform(Vector2f position, Vector2f scale)
    {
        this(new Vector3f(position, 0.0f), new Vector3f(scale, 0.0f), new Vector3f());
    }

    public Transform(float posX, float posY, float scaleX, float scaleY)
    {
        this(new Vector3f(posX, posY, 0.0f), new Vector3f(scaleX, scaleY, 0.0f), new Vector3f());
    }

    public Transform(float posX, float posY, float posZ, float scaleX, float scaleY)
    {
        this(new Vector3f(posX, posY, posZ), new Vector3f(scaleX, scaleY, 0.0f), new Vector3f());
    }

    public Transform(Vector3f position, Vector2f scale)
    {
        this(position, new Vector3f(scale, 0.0f), new Vector3f());
    }

    public Transform(Vector3f position, Vector3f scale)
    {
        this(position, scale, new Vector3f());
    }

    public Transform(float posX, float posY, float posZ, float scaleX, float scaleY, float scaleZ)
    {
        this(new Vector3f(posX, posY, posZ), new Vector3f(scaleX, scaleY, scaleZ), new Vector3f());
    }

    public Transform(Vector3f position, Vector3f scale, Vector3f rotation)
    {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Transform(float posX, float posY, float posZ, float scaleX, float scaleY, float scaleZ, float rotX,
            float rotY, float rotZ)
    {
        this(new Vector3f(posX, posY, posZ), new Vector3f(scaleX, scaleY, scaleZ), new Vector3f(rotX, rotY, rotZ));
    }

    void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    @Override
    public Transform clone()
    {
        return new Transform(position, scale, rotation);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof Transform))
            return false;

        Transform t = (Transform) o;
        return t.position.equals(position) && t.scale.equals(scale);
    }

    public void setPosition(float x, float y)
    {
        position.x = x;
        position.y = y;
        new EntityTransformEvent(POSITION, entity);
    }

    public void setPosition(float x, float y, float z)
    {
        this.position.set(x, y, z);
        new EntityTransformEvent(POSITION, entity);
    }

    public void setPosition(Vector2f position)
    {
        this.position.x = position.x;
        this.position.y = position.y;
        new EntityTransformEvent(POSITION, entity);
    }

    public void setPosition(Vector3f position)
    {
        this.position.set(position);
        new EntityTransformEvent(POSITION, entity);
    }

    public void setPositionX(float x)
    {
        position.x = x;
        new EntityTransformEvent(POSITION, entity);
    }

    public void setPositionY(float y)
    {
        position.y = y;
        new EntityTransformEvent(POSITION, entity);
    }

    public void setPositionZ(float z)
    {
        position.z = z;
        new EntityTransformEvent(POSITION, entity);
    }

    public void setScale(float x, float y)
    {
        scale.x = x;
        scale.y = y;
        new EntityTransformEvent(SCALE, entity);
    }

    public void setScale(float x, float y, float z)
    {
        this.scale.set(x, y, z);
        new EntityTransformEvent(SCALE, entity);
    }

    public void setScale(Vector2f scale)
    {
        this.scale.x = scale.x;
        this.scale.y = scale.y;
        new EntityTransformEvent(SCALE, entity);
    }

    public void setScale(Vector3f scale)
    {
        this.scale.set(scale);
        new EntityTransformEvent(SCALE, entity);
    }

    public void setScaleX(float x)
    {
        scale.x = x;
        new EntityTransformEvent(SCALE, entity);
    }

    public void setScaleY(float y)
    {
        scale.y = y;
        new EntityTransformEvent(SCALE, entity);
    }

    public void setScaleZ(float z)
    {
        scale.z = z;
        new EntityTransformEvent(SCALE, entity);
    }

    public void setRotation(float x, float y, float z)
    {
        this.rotation.set(x, y, z);
        new EntityTransformEvent(ROTATION, entity);
    }

    public void setRotation(Vector3f rotation)
    {
        this.rotation.set(rotation);
        new EntityTransformEvent(ROTATION, entity);
    }

    public void setRotationX(float x)
    {
        rotation.x = x;
        new EntityTransformEvent(ROTATION, entity);
    }

    public void setRotationY(float y)
    {
        rotation.y = y;
        new EntityTransformEvent(ROTATION, entity);
    }

    public void setRotationZ(float z)
    {
        rotation.z = z;
        new EntityTransformEvent(ROTATION, entity);
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getPositionX()
    {
        return position.x;
    }

    public float getPositionY()
    {
        return position.y;
    }

    public float getPositionZ()
    {
        return position.z;
    }

    public Vector3f getScale()
    {
        return scale;
    }

    public float getScaleX()
    {
        return scale.x;
    }

    public float getScaleY()
    {
        return scale.y;
    }

    public float getScaleZ()
    {
        return scale.z;
    }

    public Vector3f getRotation()
    {
        return rotation;
    }

    public float getRotationX()
    {
        return rotation.x;
    }

    public float getRotationY()
    {
        return rotation.y;
    }

    public float getRotationZ()
    {
        return rotation.z;
    }

    @Override
    public void serialize(Map<String, Object> fields)
    {
        fields.put("position", String.format("%f, %f, %f", position.x, position.y, position.z));
        fields.put("scale", String.format("%f, %f, %f", scale.x, scale.y, scale.z));
        fields.put("rotation", String.format("%f, %f, %f", rotation.x, rotation.y, rotation.z));
    }

    @Deserializer
    public static Transform deserialize(Map<String, String> fields)
    {
        String[] position = fields.get("position").split(",");
        String[] scale = fields.get("scale").split(",");
        String[] rotation = fields.get("rotation").split(",");
        return new Transform(Float.valueOf(position[0]), Float.valueOf(position[1]), Float.valueOf(position[2]), //
                Float.valueOf(scale[0]), Float.valueOf(scale[1]), Float.valueOf(scale[2]), //
                Float.valueOf(rotation[0]), Float.valueOf(rotation[1]), Float.valueOf(rotation[2]));
    }
}
