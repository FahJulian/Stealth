package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class ElementBuffer
{
    private final int ID;

    public ElementBuffer(int[] indices)
    {
        this.ID = create();

        this.bind();
        this.buffer(indices);
    }

    public void bind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ID);
    }

    public void unbind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ID);
    }

    private int create()
    {
        int ID = glGenBuffers();
        OpenGLMemoryManager.loadedVertexBuffers.add(ID);
        return ID;
    }

    private void buffer(int[] indices)
    {
        IntBuffer buffer = toIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    private IntBuffer toIntBuffer(int[] data)
    {
        return BufferUtils.createIntBuffer(data.length).put(data).flip();
    }
}
