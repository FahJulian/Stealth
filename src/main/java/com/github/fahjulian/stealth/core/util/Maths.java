package com.github.fahjulian.stealth.core.util;

import com.github.fahjulian.stealth.core.scene.Camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class Maths
{
    public static final Matrix4f createTransformationMatrix(Vector3f position, Vector3f scale, Vector3f rotation)
    {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(position);
        matrix.scale(scale);
        matrix.rotate((float) Math.toRadians(rotation.x), 1, 0, 0);
        matrix.rotate((float) Math.toRadians(rotation.y), 0, 1, 0);
        matrix.rotate((float) Math.toRadians(rotation.z), 0, 0, 1);
        return matrix;
    }

    public static final Vector3f mouseRay3D(float mouseX, float mouseY, Camera camera)
    {
        Matrix4f inverseView = new Matrix4f();
        camera.getViewMatrix().invert(inverseView);

        Vector4f result = new Vector4f(mouseX, mouseY, 0.0f, 1.0f).mul(inverseView);

        return new Vector3f(result.x, result.y, result.z);
    }

    public static final Vector2f mouseRay2D(float mouseX, float mouseY, Camera camera)
    {
        Matrix4f inverseView = new Matrix4f();
        camera.getViewMatrix().invert(inverseView);

        Vector4f result = new Vector4f(mouseX, mouseY, 0.0f, 1.0f).mul(inverseView);

        return new Vector2f(result.x, result.y);
    }
}
