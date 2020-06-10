package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;
import com.github.fahjulian.stealth.graphics.opengl.VertexBuffer;

public class RawModel
{
    private final VertexArray vao;
    private final int vertexCount;

    public RawModel(float[] positions, int[] indices)
    {
        this.vao = new VertexArray();
        this.vertexCount = indices.length;

        VertexBuffer vbo = new VertexBuffer(positions, 3);
        vao.addVBO(vbo);
        vbo.unbind();

        ElementBuffer ebo = new ElementBuffer(indices);
        ebo.unbind();

        vao.unbind();
    }

    void draw()
    {
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
    }

    void bind()
    {
        vao.bind();
    }

    void unbind()
    {
        vao.unbind();
    }
}
