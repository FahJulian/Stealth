package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.graphics.opengl.DynamicVertexBuffer;
import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;

public class BatchedTexturedModel
{
    private final VertexArray vao;
    private final DynamicVertexBuffer positionsVBO;
    private final DynamicVertexBuffer textureCoordsVBO;
    private final int maxRects;
    private int rectCount;
    private float[] positions;
    private float[] textureCoords;

    BatchedTexturedModel(final int maxRects)
    {
        this.vao = new VertexArray();
        this.maxRects = maxRects;
        this.rectCount = 0;
        this.positions = new float[maxRects * 4 * 3];
        this.textureCoords = new float[maxRects * 4 * 2];

        positionsVBO = new DynamicVertexBuffer(positions.length, 3);
        vao.addVBO(positionsVBO);
        positionsVBO.unbind();

        textureCoordsVBO = new DynamicVertexBuffer(textureCoords.length, 2);
        vao.addVBO(textureCoordsVBO);
        textureCoordsVBO.unbind();

        int[] indices = generateIndices(maxRects);
        ElementBuffer ebo = new ElementBuffer(indices);
        ebo.unbind();

        vao.unbind();
    }

    void clear()
    {
        positions = new float[maxRects * 4 * 3];
        textureCoords = new float[maxRects * 4 * 2];
        rectCount = 0;
    }

    void bind()
    {
        vao.bind();
    }

    void unbind()
    {
        vao.unbind();
    }

    void addRect(float x, float y, float z, float width, float height)
    {
        for (int i = 0; i < 4; i++)
        {
            positions[rectCount * (4 * 3) + i * 3 + 0] = x + (i % 2 == 0 ? width : 0);
            positions[rectCount * (4 * 3) + i * 3 + 1] = y + (i / 2 == 0 ? height : 0);
            positions[rectCount * (4 * 3) + i * 3 + 2] = z;
            textureCoords[rectCount * (4 * 2) + i * 2 + 0] = i % 2 == 0 ? 1 : 0;
            textureCoords[rectCount * (4 * 2) + i * 2 + 1] = i / 2 == 0 ? 1 : 0;
        }

        rectCount++;
    }

    void draw()
    {
        positionsVBO.bind();
        positionsVBO.buffer(positions);
        positionsVBO.unbind();

        textureCoordsVBO.bind();
        textureCoordsVBO.buffer(textureCoords);
        textureCoordsVBO.unbind();

        glDrawElements(GL_TRIANGLES, rectCount * 6, GL_UNSIGNED_INT, 0);
    }

    private int[] generateIndices(int maxRects)
    {
        int[] indices = new int[maxRects * 6];
        for (int i = 0; i < maxRects; i++)
        {
            indices[0 + i * 6] = 2 + i * 4;
            indices[1 + i * 6] = 0 + i * 4;
            indices[2 + i * 6] = 1 + i * 4;
            indices[3 + i * 6] = 3 + i * 4;
            indices[4 + i * 6] = 2 + i * 4;
            indices[5 + i * 6] = 1 + i * 4;
        }

        return indices;
    }
}
