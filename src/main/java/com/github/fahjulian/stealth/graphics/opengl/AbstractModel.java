package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

/** A Model base class that only holds a VAO and an EBO */
public abstract class AbstractModel
{
    protected final VertexArray vao;
    private final ElementBuffer ebo;

    /**
     * Construct a new model
     * 
     * @param vertexCount
     *                        The amount of vertices the model will have
     */
    protected AbstractModel(int vertexCount)
    {
        this.vao = new VertexArray();
        this.vao.bind();

        this.ebo = new ElementBuffer(generateIndices(vertexCount), vao);
    }

    /**
     * Every model needs an EBO. This method must generate the Integer Array that
     * gets buffered to the EBO.
     * 
     * @param vertexCount
     *                        The vertexCount that is passed to the constructor of
     *                        the model
     * 
     * @return The generated integer array holding the indices
     */
    abstract protected int[] generateIndices(int vertexCount);

    /** Draws the whole VAO with all the vertices */
    public void draw()
    {
        vao.bind();
        glDrawElements(GL_TRIANGLES, ebo.getSize(), GL_UNSIGNED_INT, 0);
        vao.unbind();
    }
}
