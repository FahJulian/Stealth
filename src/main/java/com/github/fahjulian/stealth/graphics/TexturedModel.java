package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;
import com.github.fahjulian.stealth.graphics.opengl.VertexBuffer;

public class TexturedModel
{
    private final VertexArray vao;
    private final int vertexCount;

    public TexturedModel(float[] positions, float[] textureCoords, int[] indices)
    {
        this.vao = new VertexArray();
        this.vertexCount = indices.length;

        VertexBuffer positionVBO = new VertexBuffer(positions, 3);
        vao.addVBO(positionVBO);
        positionVBO.unbind();

        VertexBuffer textureCoordsVBO = new VertexBuffer(textureCoords, 2);
        vao.addVBO(textureCoordsVBO);
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

    void draw()
    {
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
    }
}
