package com.github.fahjulian.stealth.graphics.renderer;

import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.graphics.opengl.ElementBuffer;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.opengl.VertexArray;

public abstract class AbstractIndependentModel
{
    protected final VertexArray vao;
    protected ElementBuffer ebo;
    protected Shader shader;

    protected AbstractIndependentModel(Shader shader)
    {
        this.vao = new VertexArray();
        this.shader = shader;
    }

    protected void setIndicesBuffer(int[] indices)
    {
        this.ebo = new ElementBuffer(indices, vao);
    }

    abstract public void draw(Camera camera);
}
