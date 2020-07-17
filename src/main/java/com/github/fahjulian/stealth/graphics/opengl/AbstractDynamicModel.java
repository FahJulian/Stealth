package com.github.fahjulian.stealth.graphics.opengl;

/* A model that has dynamic vbos */
public abstract class AbstractDynamicModel extends AbstractModel
{
    protected final DynamicVertexBuffer[] vbos;

    /**
     * Construct a new Dynamic model
     * 
     * @param vertexCount
     *                        The amount of vertices in the model
     * @param vboSizes
     *                        The stride sizes of each vbo. For example a model with
     *                        a vbo for position and color would be 3, 4
     */
    protected AbstractDynamicModel(int vertexCount, int... vboSizes)
    {
        super(vertexCount);

        vbos = new DynamicVertexBuffer[vboSizes.length];
        for (int i = 0; i < vboSizes.length; i++)
            vbos[i] = new DynamicVertexBuffer(new float[vertexCount * vboSizes[i]], vboSizes[i], vao);

        vao.unbind();
    }

    /**
     * Sets the vertec at the given index to the given vertex
     * 
     * @param index
     *                   The index of the vertex to change
     * @param vertex
     *                   The full vertex (All vbos added together, e.g. 10.0f,
     *                   10.0f, 0.0f, 0.5f, 0.1f, 0.1f, 0.2f for position and color)
     */
    protected void setVertex(int index, float... vertex)
    {
        int x = 0;
        for (int i = 0; i < vbos.length; i++)
        {
            DynamicVertexBuffer vbo = vbos[i];
            for (int j = 0; j < vbo.strideSize; j++)
                vbo.data[j + index * vbo.strideSize] = vertex[x++];
        }
    }

    /**
     * Clears all the vbos in the model. Can be made public by subclasses
     */
    protected void clear()
    {
        for (DynamicVertexBuffer vbo : vbos)
            vbo.clear();
    }

    /**
     * Rebuffers all the vbos in the model. Can be made public by subclasses
     */
    protected void rebuffer()
    {
        for (DynamicVertexBuffer vbo : vbos)
            vbo.rebuffer();
    }
}
