package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;

import java.nio.FloatBuffer;

/** A dynamic VBO */
public class DynamicVertexBuffer
{
    private final int ID;
    final int strideSize;
    final float[] data;

    /**
     * Construct a new empty dynamic VBO
     * 
     * @param arrayPointer
     *                         Pointer to the array holding the data of the VBO.
     *                         DynamicVertexBuffer will keep track of this pointer
     *                         to rebuffer data.
     * @param strideSize
     *                         The stride size withing the VBO.
     * @param vao
     *                         The VAO the VBO is part of.
     */
    public DynamicVertexBuffer(float[] arrayPointer, int strideSize, VertexArray vao)
    {
        this.ID = OpenGLMemoryManager.createVertexBuffer();
        this.strideSize = strideSize;
        this.data = arrayPointer;

        vao.bind();
        this.bind();
        glBufferData(GL_ARRAY_BUFFER, data.length * Float.BYTES, GL_DYNAMIC_DRAW);
        vao.addVBO(strideSize);
        this.unbind();
        vao.unbind();
    }

    /**
     * Bind the VBO to the graphics card.
     */
    public void bind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, this.ID);
    }

    /**
     * Unbinds the VBO from the graphics card.
     */
    public void unbind()
    {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Rebuffers the data in the arrayPointer to the gpu
     */
    public void rebuffer()
    {
        glBindBuffer(GL_ARRAY_BUFFER, this.ID);

        FloatBuffer buffer = OpenGLMemoryManager.toFloatBuffer(data);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        unbind();
    }

    /**
     * Clears all the data in the array
     */
    public void clear()
    {
        for (int i = 0; i < data.length; i++)
            data[i] = 0.0f;
    }
}
