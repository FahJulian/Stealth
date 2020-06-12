package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;
import com.github.fahjulian.stealth.graphics.opengl.VertexBuffer;

public class ColoredModel
{
    private final VertexArray vao;
    private final int vertexCount;

    ColoredModel(float[] positions, float[] colors, int[] indices)
    {
        this.vao = new VertexArray();
        this.vertexCount = indices.length;

        VertexBuffer positionVBO = new VertexBuffer(positions, 3);
        vao.addVBO(positionVBO);
        positionVBO.unbind();

        VertexBuffer colorVBO = new VertexBuffer(colors, 4);
        vao.addVBO(colorVBO);
        colorVBO.unbind();

        ElementBuffer ebo = new ElementBuffer(indices);
        ebo.unbind();

        vao.unbind();
    }

    void bind()
    {
        vao.bind();
    }

    void unbind()
    {
        vao.unbind();
    }

    int getVertexCount()
    {
        return vertexCount;
    }
}
