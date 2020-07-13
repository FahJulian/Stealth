package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class VertexArray
{
    private final int ID;
    private int vboCount;

    public VertexArray()
    {
        this.ID = OpenGLMemoryManager.createVertexArray();
    }

    public void bind()
    {
        glBindVertexArray(this.ID);
        for (int i = 0; i < vboCount; i++)
            glEnableVertexAttribArray(i);
    }

    public void unbind()
    {
        for (int i = 0; i < vboCount; i++)
            glDisableVertexAttribArray(i);
        glBindVertexArray(0);
    }

    public void addVBO(int strideSize)
    {
        glVertexAttribPointer(vboCount++, strideSize, GL_FLOAT, false, strideSize * Float.BYTES, 0);
    }
}
