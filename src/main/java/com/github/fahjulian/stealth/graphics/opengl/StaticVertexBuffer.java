package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.FloatBuffer;

/** A VBO that's data can't be changed */
public class StaticVertexBuffer
{
    private final int ID;

    /**
     * Construct a new VBO
     * 
     * @param data
     *                       The data to buffer to the gpu
     * @param strideSize
     *                       The stride size within the vbo
     * @param vao
     *                       The vao the vbo is part of
     */
    public StaticVertexBuffer(float[] data, int strideSize, VertexArray vao)
    {
        this.ID = OpenGLMemoryManager.createVertexBuffer();

        vao.bind();
        glBindBuffer(GL_ARRAY_BUFFER, this.ID);
        FloatBuffer buffer = OpenGLMemoryManager.toFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        vao.addVBO(strideSize);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        vao.unbind();
    }
}
