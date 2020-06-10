package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray
{
    private final int ID;
    private int vboCount;

    public VertexArray()
    {
        this.ID = create();
        this.bind();
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

    public void addVBO(VertexBuffer vbo)
    {
        glVertexAttribPointer(vboCount++, vbo.vertexSize, GL_FLOAT, false,
                vbo.vertexSize * Float.BYTES, 0);
    }

    private int create()
    {
        int ID = glGenVertexArrays();
        OpenGLMemoryManager.loadedVertexArrays.add(ID);
        return ID;
    }
}
