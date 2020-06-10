package com.github.fahjulian.stealth.core.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class Maths
{
    public static final Matrix4f createTransformationMatrix(Vector3f position, Vector3f scale,
            Vector3f rotation)
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
}
