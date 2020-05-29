package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.github.fahjulian.stealth.core.Log;

import org.lwjgl.BufferUtils;

public class Texture {

	private final String filePath;
	private final int width, height;
	private int ID;

	/**
	 * Create a texture from an image file
	 * @param filePath The path to the image file on the system
	 */
	public Texture(String filePath) {
		this.filePath = filePath;

		this.ID = glGenTextures();
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, this.ID);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = stbi_load(filePath, width, height, channels, 0);

		this.width = width.get(0);
		this.height = height.get(0);
		int format = (channels.get(0) == 3) ? GL_RGB : (channels.get(0) == 4) ? GL_RGBA: -1;
		
		if (image == null || format == -1) 
			Log.error("(Texture) Could not load image %s", filePath);

		glTexImage2D(GL_TEXTURE_2D, 0, format, width.get(0), height.get(0), 0, format, GL_UNSIGNED_BYTE, image);
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, this.ID);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

    @Override
    public String toString() {
		return filePath;
	}
}
