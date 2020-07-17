package com.github.fahjulian.stealth.graphics.renderer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.DynamicVertexBuffer;

public class BatchedTexturedModel extends AbstractModel
{
    private final DynamicVertexBuffer positionsVBO;
    private final DynamicVertexBuffer textureCoordsVBO;
    private final DynamicVertexBuffer textureSlotsVBO;
    private final int maxRects;
    private int rectCount;
    private float[] positions;
    private float[] textureCoords;
    private float[] textureSlots;

    public BatchedTexturedModel(final int maxRects)
    {
        this.maxRects = maxRects;
        this.rectCount = 0;

        this.positions = new float[maxRects * 4 * 3];
        this.textureCoords = new float[maxRects * 4 * 2];
        this.textureSlots = new float[maxRects * 4 * 1];

        positionsVBO = new DynamicVertexBuffer(positions, 3, vao);
        textureCoordsVBO = new DynamicVertexBuffer(textureCoords, 2, vao);
        textureSlotsVBO = new DynamicVertexBuffer(textureSlots, 1, vao);

        setIndicesBuffer(generateIndices(maxRects));
    }

    @Override
    public void draw()
    {
        vao.bind();

        glDrawElements(GL_TRIANGLES, rectCount * 6, GL_UNSIGNED_INT, 0);

        vao.unbind();
    }

    public void clear()
    {
        positionsVBO.clear();
        textureCoordsVBO.clear();
        textureSlotsVBO.clear();

        rectCount = 0;
    }

    public void addRect(float x, float y, float z, float width, float height, float[] textureCoords, int textureSlot)
    {
        if (rectCount >= maxRects)
        {
            Log.warn("(BatchedTexturedModel) Maximum rect amount reached.");
            return;
        }

        for (int i = 0; i < 4; i++)
        {
            this.positions[rectCount * (4 * 3) + i * 3 + 0] = x + (1 - i % 2) * width;
            this.positions[rectCount * (4 * 3) + i * 3 + 1] = y + (1 - i / 2) * height;
            this.positions[rectCount * (4 * 3) + i * 3 + 2] = z;
            this.textureCoords[rectCount * (4 * 2) + i * 2 + 0] = textureCoords[i * 2 + 0];
            this.textureCoords[rectCount * (4 * 2) + i * 2 + 1] = textureCoords[i * 2 + 1];
            this.textureSlots[rectCount * (4 * 1) + i * 1 + 0] = (float) textureSlot;
        }

        rectCount++;
    }

    public void rebuffer()
    {
        positionsVBO.rebuffer();
        positionsVBO.unbind();

        textureCoordsVBO.rebuffer();
        textureCoordsVBO.unbind();

        textureSlotsVBO.rebuffer();
        textureSlotsVBO.unbind();
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
