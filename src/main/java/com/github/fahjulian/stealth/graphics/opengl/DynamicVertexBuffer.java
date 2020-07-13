package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;

import java.nio.FloatBuffer;

public class DynamicVertexBuffer
{
    private final int ID;
    private float[] data;

    public DynamicVertexBuffer(float[] arrayPointer, int strideSize, VertexArray vao)
    {
        this.ID = OpenGLMemoryManager.createVertexBuffer();
        this.data = arrayPointer;

        vao.bind();
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, data.length * Float.BYTES, GL_DYNAMIC_DRAW);
        vao.addVBO(strideSize);
        this.unbind();
        vao.unbind();
    }

    public void bind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, this.ID);
    }

    public void unbind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void rebuffer()
    {
        glBindBuffer(GL_ARRAY_BUFFER, this.ID);

        FloatBuffer buffer = OpenGLMemoryManager.toFloatBuffer(data);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
    }

    public void clear()
    {
        for (int i = 0; i < data.length; i++)
            data[i] = 0.0f;
    }
}
