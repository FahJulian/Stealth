package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;

import java.nio.FloatBuffer;

public class StaticVertexBuffer
{
	private final int ID;

	public StaticVertexBuffer(float[] data, int strideSize, VertexArray vao)
	{
		this.ID = OpenGLMemoryManager.createVertexBuffer();

		vao.bind();
		this.bind();
		FloatBuffer buffer = OpenGLMemoryManager.toFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		vao.addVBO(strideSize);
		this.unbind();
		vao.unbind();
	}

	public void bind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, this.ID);
	}

	public void unbind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
