package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.IntBuffer;

public class ElementBuffer
{
    private final int ID;
    private final int size;

    public ElementBuffer(int[] indices, VertexArray vao)
    {
        this.ID = OpenGLMemoryManager.createVertexBuffer();
        this.size = indices.length;

        vao.bind();
        this.bind();
        IntBuffer buffer = OpenGLMemoryManager.toIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        this.unbind();
        vao.unbind();
    }

    public void bind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ID);
    }

    public void unbind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ID);
    }

    public int getSize()
    {
        return size;
    }
}
