package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/** Wrapper for an opengl VAO */
public class VertexArray
{
    private final int ID;
    private int vboCount;

    /** Construct a new VAO */
    public VertexArray()
    {
        this.ID = OpenGLMemoryManager.createVertexArray();
    }

    /** Bind the VAO to the gpu */
    public void bind()
    {
        glBindVertexArray(this.ID);
        for (int i = 0; i < vboCount; i++)
            glEnableVertexAttribArray(i);
    }

    /** Unbind the VAO from the gpu */
    public void unbind()
    {
        for (int i = 0; i < vboCount; i++)
            glDisableVertexAttribArray(i);
        glBindVertexArray(0);
    }

    /**
     * Add a VBO to the VAO
     * 
     * @param strideSize
     *                       The stride size within the added VBO
     */
    public void addVBO(int strideSize)
    {
        glVertexAttribPointer(vboCount++, strideSize, GL_FLOAT, false, strideSize * Float.BYTES, 0);
    }
}
