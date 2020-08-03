package com.github.fahjulian.stealth.core.scene;

import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.core.util.Maths;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * The Camera holds matricies defining what area of the screen gets rendered.
 */
public final class Camera
{
    private final Matrix4f projectionMatrix;
    private final Vector3f position;
    private final Vector3f rotation;
    private Matrix4f viewMatrix;

    /**
     * Construct a new camera
     * 
     * @param posX
     *                 The x-Position of the camera
     * @param posY
     *                 The x-Position of the camera
     */
    public Camera(float posX, float posY)
    {
        this.position = new Vector3f(posX, posY, 0.0f);
        this.rotation = new Vector3f();
        this.projectionMatrix = new Matrix4f();

        updateProjectionMatrix();
        updateViewMatrix();
    }

    private void updateViewMatrix()
    {
        viewMatrix = Maths.createTransformationMatrix(new Vector3f(-position.x, -position.y, -position.z),
                new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(-rotation.x, -rotation.y, -rotation.z));
    }

    private void updateProjectionMatrix()
    {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, Window.get().getWidth(), Window.get().getHeight(), 0.0f, -10.0f, 10.0f);
    }

    public float getPositionX()
    {
        return this.position.x;
    }

    public float getPositionY()
    {
        return this.position.y;
    }

    public Matrix4f getViewMatrix()
    {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    public void setPosition(float posX, float posY)
    {
        this.position.x = posX;
        this.position.y = posY;
        updateViewMatrix();
    }
}
