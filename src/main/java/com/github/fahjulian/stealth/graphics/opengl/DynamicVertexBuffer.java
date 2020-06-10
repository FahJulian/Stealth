package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;

import java.nio.FloatBuffer;

import com.github.fahjulian.stealth.core.util.Log;

public class DynamicVertexBuffer extends VertexBuffer
{
    private final int size;

    public DynamicVertexBuffer(int size, int vertexSize)
    {
        super(vertexSize);

        this.size = size;
        bufferNullData();
    }

    @Override
    public void buffer(float[] data)
    {
        if (data.length != size)
        {
            Log.error("(DynamicVertexBuffer) float[] data must have length $d", size);
            return;
        }

        FloatBuffer buffer = toFloatBuffer(data);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
    }

    private void bufferNullData()
    {
        glBufferData(GL_ARRAY_BUFFER, size * Float.BYTES, GL_DYNAMIC_DRAW);
    }
}
