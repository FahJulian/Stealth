package com.github.fahjulian.stealth.core.entity;

import com.github.fahjulian.stealth.events.entity.EntityTransformEvent;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent.Type;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * A Transform is a data set that hold information about an entities position
 * and scale.
 */
public final class Transform
{
    private Entity entity;
    private final Vector3f position;
    private final Vector3f scale;
    private final Vector3f rotation;

    /**
     * Construct a new emtpy transform
     */
    public Transform()
    {
        this(new Vector3f(), new Vector3f());
    }

    /**
     * Construct a transform with a position but no scale
     * 
     * @param position
     *                     The position to initialize with
     */
    public Transform(Vector2f position)
    {
        this(new Vector3f(position, 0.0f), new Vector3f());
    }

    public Transform(Vector3f position)
    {
        this(position, new Vector3f());
    }

    public Transform(float posX, float posY)
    {
        this(new Vector3f(posX, posY, 0.0f), new Vector3f());
    }

    /**
     * Construct a transform with a position but not scale
     * 
     * @param posX
     *                 The x coordinate to initialize with
     * @param posY
     *                 The y coordinate to initialize with
     */
    public Transform(float posX, float posY, float posZ)
    {
        this(new Vector3f(posX, posY, posZ), new Vector3f());
    }

    /**
     * Construct a transform with a position and scale
     * 
     * @param position
     *                     The position to initialize with
     * @param scale
     *                     The scale to initialize with
     */
    public Transform(Vector2f position, Vector2f scale)
    {
        this(new Vector3f(position, 0.0f), new Vector3f(scale, 0.0f));
    }

    /**
     * Construct a transform with a position and scale
     * 
     * @param posX
     *                   The x coordinate to initialize with
     * @param posY
     *                   The y coordinate to initialize with
     * @param scaleX
     *                   The width to initialize with
     * @param scaleY
     *                   The height to initialize with
     */
    public Transform(float posX, float posY, float scaleX, float scaleY)
    {
        this(new Vector3f(posX, posY, 0.0f), new Vector3f(scaleX, scaleY, 0.0f));
    }

    /**
     * Construct a transform with a position, scale and zIndex
     * 
     * @param position
     *                     The position to initialize with
     * @param scale
     *                     The scale to initialize with
     * @param zIndex
     *                     The zIndex to initialized with (The higher the closer to
     *                     the camera)
     */
    public Transform(Vector3f position, Vector3f scale)
    {
        this(position, scale, new Vector3f());
    }

    public Transform(Vector3f position, Vector3f scale, Vector3f rotation)
    {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
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
        setPosition(x, y, 0.0f);
    }

    public void setPosition(float x, float y, float z)
    {
        this.position.set(x, y, z);
        new EntityTransformEvent(Type.POSITION, entity);
    }

    public void setPosition(Vector2f position)
    {
        setPosition(new Vector3f(position, 0.0f));
    }

    public void setPosition(Vector3f position)
    {
        this.position.set(position);
        new EntityTransformEvent(Type.POSITION, entity);
    }

    public void setRotationX(float x)
    {
        setRotation(x, 0.0f, 0.0f);
    }

    public void setRotationY(float y)
    {
        setRotation(0.0f, y, 0.0f);
    }

    public void setRotationZ(float z)
    {
        setRotation(0.0f, 0.0f, z);
    }

    public void setScale(float x, float y)
    {
        setScale(x, y, 0.0f);
    }

    public void setScale(float x, float y, float z)
    {
        this.scale.set(x, y, z);
        new EntityTransformEvent(Type.SCALE, entity);
    }

    public void setScale(Vector2f scale)
    {
        setScale(new Vector3f(scale, 0.0f));
    }

    public void setScale(Vector3f scale)
    {
        this.scale.set(scale);
        new EntityTransformEvent(Type.SCALE, entity);
    }

    public void setScaleX(float x)
    {
        setScale(x, 0.0f, 0.0f);
    }

    public void setScaleY(float y)
    {
        setScale(0.0f, y, 0.0f);
    }

    public void setScaleZ(float z)
    {
        setScale(0.0f, 0.0f, z);
    }

    public void setRotation(float x, float y, float z)
    {
        this.rotation.set(x, y, z);
        new EntityTransformEvent(Type.ROTATION, entity);
    }

    public void setRotation(Vector3f rotation)
    {
        this.rotation.set(rotation);
        new EntityTransformEvent(Type.ROTATION, entity);
    }

    public void setPositionX(float x)
    {
        setPosition(x, 0.0f, 0.0f);
    }

    public void setPositionY(float y)
    {
        setPosition(0.0f, y, 0.0f);
    }

    public void setPositionZ(float z)
    {
        setPosition(0.0f, 0.0f, z);
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
}
