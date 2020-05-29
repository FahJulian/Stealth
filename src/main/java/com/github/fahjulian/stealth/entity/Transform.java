package com.github.fahjulian.stealth.entity;

import org.joml.Vector2f;

/**
 * A Transform is a data set that hold information about an entities
 * position and scale.
 */
public final class Transform {
    private final Vector2f position;
    private final Vector2f scale;
    private int zIndex;

    /**
     * Construct a new emtpy transform
     */
    public Transform() {
        this(new Vector2f(), new Vector2f(), 0);
    }

    /**
     * Construct a transform with a position but no scale
     * @param position The position to initialize with
     */
    public Transform(Vector2f position) {
        this(position, new Vector2f(), 0);
    }

    /**
     * Construct a transform with a position but not scale
     * @param posX The x coordinate to initialize with
     * @param posY The y coordinate to initialize with
     */
    public Transform(float posX, float posY) {
        this(new Vector2f(posX, posY), new Vector2f(), 0);
    }

    /**
     * Construct a transform with a position and a zIndex 
     * @param position The position to initialize with
     * @param zIndex The zIndex to initialized with (The higher the closer to the camera)
     */
    public Transform(Vector2f position, int zIndex) {
        this(position, new Vector2f(), 0);
    }

    /**
     * Construct a transform with a position and zIndex 
     * @param posX The x coordinate to initialize with
     * @param posY The y coordinate to initialize with
     * @param zIndex The zIndex to initialized with (The higher the closer to the camera)
     */
    public Transform(float posX, float posY, int zIndex) {
        this(new Vector2f(posX, posY), new Vector2f(), zIndex);
    }

    /**
     * Construct a transform with a position and scale
     * @param position The position to initialize with
     * @param scale The scale to initialize with
     */
    public Transform(Vector2f position, Vector2f scale) {
        this(position, scale, 0);
    }

    /**
     * Construct a transform with a position and scale
     * @param posX The x coordinate to initialize with
     * @param posY The y coordinate to initialize with
     * @param scaleX The width to initialize with
     * @param scaleY The height to initialize with
     */
    public Transform(float posX, float posY, float scaleX, float scaleY) {
        this(new Vector2f(posX, posY), new Vector2f(scaleX, scaleY), 0);
    }

    /**
     * Construct a transform with a position, scale and zIndex 
     * @param position The position to initialize with
     * @param scale The scale to initialize with
     * @param zIndex The zIndex to initialized with (The higher the closer to the camera)
     */
    public Transform(Vector2f position, Vector2f scale, int zIndex) {
        this.position = position;
        this.scale = scale;
        this.zIndex = zIndex;
    }

    /**
     * Construct a transform with a position, scale and zIndex 
     * @param posX The x coordinate to initialize with
     * @param posY The y coordinate to initialize with
     * @param scaleX The width to initialize with
     * @param scaleY The height to initialize with
     * @param zIndex The zIndex to initialized with (The higher the closer to the camera)
     */
    public Transform(float posX, float posY, float scaleX, float scaleY, int zIndex) {
        this(new Vector2f(posX, posY), new Vector2f(scaleX, scaleY), zIndex);
    }

    @Override
    public Transform clone() {
        return new Transform(position, scale, zIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Transform))
            return false;
                            
        Transform t = (Transform) o;
        return t.position.equals(position) && t.scale.equals(scale) && t.zIndex == zIndex;
    }

    public void setPosition(float x, float y) {
        this.position.set(x , y);
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
    }

    public void setScale(float x, float y) {
        this.scale.set(x, y);
    }

    public void setScale(Vector2f scale) {
        this.scale.set(scale);
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public int getZIndex() {
        return zIndex;
    }
}
