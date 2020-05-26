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
    private boolean hasChanges;

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
     * Construct a transform with a position and a zIndex 
     * @param position The position to initialize with
     * @param zIndex The zIndex to initialized with (The higher the closer to the camera)
     */
    public Transform(Vector2f position, int zIndex) {
        this(position, new Vector2f(), 0);
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
     * Construct a transform with a position, scale and zIndex 
     * @param position The position to initialize with
     * @param scale The scale to initialize with
     * @param zIndex The zIndex to initialized with (The higher the closer to the camera)
     */
    public Transform(Vector2f position, Vector2f scale, int zIndex) {
        this.position = position;
        this.scale = scale;
        this.zIndex = zIndex;
        hasChanges = true;
    }

    public void setPosition(float x, float y) {
        this.position.set(x , y);
        hasChanges = true;
    }

    public void setPosition(Vector2f position) {
        this.position.set(position);
        hasChanges = true;
    }

    public void setScale(float x, float y) {
        this.scale.set(x, y);
        hasChanges = true;
    }

    public void setScale(Vector2f scale) {
        this.scale.set(scale);
        hasChanges = true;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        hasChanges = true;
    }

    /**
     * Mark all changes the transform has as handled.
     * Mostly for rendering.
     */
    public void cleanChages() {
        hasChanges = false;
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

    /**
     * Check for changes in the transform. Mostly for rendering
     * @return Whether or not the transform has changed since the last check.
     */
    public boolean hasChanges() {
        return hasChanges;
    }
}
