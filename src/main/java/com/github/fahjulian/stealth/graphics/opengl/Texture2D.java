package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.github.fahjulian.stealth.core.util.Log;

import org.lwjgl.BufferUtils;

public class Texture2D
{
    class Data
    {
        private final int width, height;
        private final ByteBuffer pixels;

        private Data(int width, int height, ByteBuffer pixels)
        {
            this.width = width;
            this.height = height;
            this.pixels = pixels;
        }
    }

    private final int ID;
    private final Data data;

    public Texture2D(String imagePath)
    {
        this.ID = create();

        bind();
        setOpenGLParams();
        this.data = load(imagePath);

        if (this.data == null)
        {
            Log.error("Texture2D) Could not load texture from file %s.", imagePath);
            return;
        }
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, this.ID);
    }

    public void unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth()
    {
        return data.width;
    }

    public int getHeight()
    {
        return data.height;
    }

    private int create()
    {
        int ID = glGenTextures();
        OpenGLMemoryManager.loadedTextures.add(ID);
        return ID;
    }

    private void setOpenGLParams()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    private Data load(String imagePath)
    {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);
        ByteBuffer pixels = stbi_load(imagePath, width, height, channels, 4);

        if (pixels == null)
            return null;

        Data data = new Data(width.get(0), height.get(0), pixels);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, data.width, data.height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, data.pixels);

        return data;
    }
}
