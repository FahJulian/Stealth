package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.IntBuffer;

/** EBO Wrapper */
public class ElementBuffer
{
    private final int ID;

    /**
     * Construct a new element buffer
     * 
     * @param indices
     *                    The indices to buffer to the graphics card
     * @param vao
     *                    The VAO the EBO is part of
     */
    public ElementBuffer(int[] indices, VertexArray vao)
    {
        this.ID = OpenGLMemoryManager.createVertexBuffer();

        vao.bind();
        this.bind();
        IntBuffer buffer = OpenGLMemoryManager.toIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        this.unbind();
        vao.unbind();
    }

    /** Binds the ebo to the graphics card */
    public void bind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ID);
    }

    /** Unbinds the ebo from the graphics card. */
    public void unbind()
    {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ID);
    }
}
