package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class VertexBuffer
{
	private final int ID;
	final int vertexSize;

	public VertexBuffer(float[] data, int vertexSize)
	{
		this.ID = create();
		this.vertexSize = vertexSize;

		this.bind();
		buffer(data);
	}

	public void buffer(float[] data)
	{
		FloatBuffer buffer = toFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}

	public void bind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, this.ID);
	}

	public void unbind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private int create()
	{
		int ID = glGenBuffers();
		OpenGLMemoryManager.loadedVertexBuffers.add(ID);
		return ID;
	}

	private FloatBuffer toFloatBuffer(float[] data)
	{
		return BufferUtils.createFloatBuffer(data.length).put(data).flip();
	}
}
