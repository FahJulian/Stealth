package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;
import com.github.fahjulian.stealth.graphics.opengl.VertexBuffer;

public class TexturedModel
{
    private final VertexArray vao;
    private final int vertexCount;

    public TexturedModel(float[] positions, float[] textureCoords, float[] textureSlots, int[] indices)
    {
        this.vao = new VertexArray();
        this.vertexCount = indices.length;

        VertexBuffer positionVBO = new VertexBuffer(positions, 3);
        vao.addVBO(positionVBO);
        positionVBO.unbind();

        VertexBuffer textureCoordsVBO = new VertexBuffer(textureCoords, 2);
        vao.addVBO(textureCoordsVBO);
        textureCoordsVBO.unbind();

        VertexBuffer textureSlotsVBO = new VertexBuffer(textureSlots, 1);
        vao.addVBO(textureSlotsVBO);
        textureCoordsVBO.unbind();

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
