package com.github.fahjulian.stealth.graphics.renderer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.opengl.AbstractDynamicModel;

/** A model that holds colored rects. Mostly used for the 2D batch renderer. */
public class BatchedColoredModel extends AbstractDynamicModel
{
    private final int maxRects;
    private int rectCount;

    /**
     * Construct a new colored model.
     * 
     * @param maxRects
     *                     The maximum amount of rects the model is allowed to hold.
     */
    public BatchedColoredModel(int maxRects)
    {
        super(maxRects * 4, 3, 4);

        this.maxRects = maxRects;
        this.rectCount = 0;
    }

    /**
     * Add a new rectangle to the model.
     * 
     * @param x
     *                   The x-position of the new rectangle
     * @param y
     *                   The y-position of the new rectangle
     * @param z
     *                   The z-position of the new rectangle
     * @param width
     *                   The width of the new rectangle
     * @param height
     *                   The height of the new rectangle
     * @param color
     *                   The color of the new rectangle
     */
    public void addRect(float x, float y, float z, float width, float height, Color color)
    {
        if (rectCount >= maxRects)
        {
            Log.warn("(BatchedColoredModel) Maximum rect amount reached.");
            return;
        }

        for (int i = 0; i < 4; i++)
        {
            int idx = rectCount * 4 + i;
            super.setVertex(idx, x + (i % 2 == 0 ? width : 0), y + (i / 2 == 0 ? height : 0), z, color.getR(),
                    color.getG(), color.getB(), color.getA());
        }

        rectCount++;
    }

    @Override
    public void draw()
    {
        vao.bind();
        glDrawElements(GL_TRIANGLES, rectCount * 6, GL_UNSIGNED_INT, 0);
        vao.unbind();
    }

    @Override
    public void clear()
    {
        super.clear();
        rectCount = 0;
    }

    @Override
    public void rebuffer()
    {
        super.rebuffer();
    }

    @Override
    protected int[] generateIndices(int vertexCount)
    {
        int[] indices = new int[vertexCount];
        for (int i = 0; i < vertexCount / 6; i++)
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
