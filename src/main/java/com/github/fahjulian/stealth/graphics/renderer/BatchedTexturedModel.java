package com.github.fahjulian.stealth.graphics.renderer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.AbstractDynamicModel;

/** A model that holds textured rects. Mostly used for the 2D batch renderer. */
public class BatchedTexturedModel extends AbstractDynamicModel
{
    private final int maxRects;
    private int rectCount;

    /**
     * Construct a new textured model.
     * 
     * @param maxRects
     *                     The maximum amount of rects the model is allowed to hold.
     */
    public BatchedTexturedModel(int maxRects)
    {
        super(maxRects * 4, 3, 2, 1);

        this.rectCount = 0;
        this.maxRects = maxRects;
    }

    /**
     * Add a new rectangle to the model.
     * 
     * @param x
     *                          The x-position of the new rectangle
     * @param y
     *                          The y-position of the new rectangle
     * @param z
     *                          The z-position of the new rectangle
     * @param width
     *                          The width of the new rectangle
     * @param height
     *                          The height of the new rectangle
     * @param textureCoords
     *                          The coordinates on the texture of the new rectangle
     * @param textureSlot
     *                          The slot of the texture on the gpu
     */
    public void addRect(float x, float y, float z, float width, float height, float[] textureCoords, int textureSlot)
    {
        if (rectCount >= maxRects)
        {
            Log.warn("(BatchedTexturedModel) Maximum rect amount reached.");
            return;
        }

        for (int i = 0; i < 4; i++)
        {
            int idx = rectCount * 4 + i;
            super.setVertex(idx, x + (1 - i % 2) * width, y + (1 - i / 2) * height, z, textureCoords[i * 2 + 0],
                    textureCoords[i * 2 + 1], textureSlot);
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
