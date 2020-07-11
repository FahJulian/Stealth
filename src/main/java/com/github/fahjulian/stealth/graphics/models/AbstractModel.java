package com.github.fahjulian.stealth.graphics.models;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;

public abstract class AbstractModel
{
    protected final VertexArray vao;
    protected ElementBuffer ebo;

    protected AbstractModel()
    {
        this.vao = new VertexArray();
    }

    protected void setIndicesBuffer(int[] indices)
    {
        this.ebo = new ElementBuffer(indices, vao);
    }

    public void draw()
    {
        vao.bind();

        glDrawElements(GL_TRIANGLES, ebo.getSize(), GL_UNSIGNED_INT, 0);

        vao.unbind();
    }
}
