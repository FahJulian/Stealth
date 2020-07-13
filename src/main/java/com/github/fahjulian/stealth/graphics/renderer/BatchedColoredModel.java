package com.github.fahjulian.stealth.graphics.renderer;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.opengl.DynamicVertexBuffer;

public class BatchedColoredModel extends AbstractModel
{
    private final DynamicVertexBuffer positionsVBO;
    private final DynamicVertexBuffer colorsVBO;
    private final int maxRects;
    private int rectCount;
    private float[] positions;
    private float[] colors;

    public BatchedColoredModel(final int maxRects)
    {
        this.maxRects = maxRects;
        this.rectCount = 0;

        this.positions = new float[maxRects * 4 * 3];
        this.colors = new float[maxRects * 4 * 4];

        positionsVBO = new DynamicVertexBuffer(positions, 3, vao);
        colorsVBO = new DynamicVertexBuffer(colors, 4, vao);

        setIndicesBuffer(generateIndices(maxRects));
    }

    public void clear()
    {
        positions = new float[maxRects * 4 * 3];
        colors = new float[maxRects * 4 * 4];
        rectCount = 0;
    }

    public void addRect(float x, float y, float z, float width, float height, Color color)
    {
        if (rectCount == maxRects)
        {
            Log.warn("(BatchedTexturedModel) Maximum rect amount reached.");
            return;
        }

        for (int i = 0; i < 4; i++)
        {
            positions[rectCount * (4 * 3) + i * 3 + 0] = x + (i % 2 == 0 ? width : 0);
            positions[rectCount * (4 * 3) + i * 3 + 1] = y + (i / 2 == 0 ? height : 0);
            positions[rectCount * (4 * 3) + i * 3 + 2] = z;
            colors[rectCount * (4 * 4) + i * 4 + 0] = color.getR();
            colors[rectCount * (4 * 4) + i * 4 + 1] = color.getG();
            colors[rectCount * (4 * 4) + i * 4 + 2] = color.getB();
            colors[rectCount * (4 * 4) + i * 4 + 3] = color.getA();
        }

        rectCount++;
    }

    public void rebuffer()
    {
        positionsVBO.rebuffer();
        positionsVBO.unbind();

        colorsVBO.rebuffer();
        colorsVBO.unbind();
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

    public int getRectCount()
    {
        return rectCount;
    }
}
