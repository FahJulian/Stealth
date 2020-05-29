package com.github.fahjulian.stealth.scene;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * The Camera holds matricies defining what area of the screen gets rendered.
 */
public class Camera {
    
    private final Vector2f position;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;

    /**
     * Construct a new camera
     * @param posX The x-Position of the camera
     * @param posY The x-Position of the camera
     */
    public Camera(float posX, float posY) {
        this.position = new Vector2f(posX, posY);
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    private void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        this.viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f), cameraFront.add(position.x, position.y, 0.0f), cameraUp);
        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
