package com.github.fahjulian.stealth.graphics.opengl;

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
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.resources.IResource;
import com.github.fahjulian.stealth.resources.IResourceBlueprint;

import org.lwjgl.BufferUtils;

public class Texture2D implements IResource
{
    public static class Blueprint implements IResourceBlueprint<Texture2D>
    {
        private final String filePath;

        public Blueprint(String filePath)
        {
            this.filePath = filePath;
        }

        @Override
        public boolean equals(IResourceBlueprint<Texture2D> blueprint)
        {
            return ((Blueprint) blueprint).filePath == this.filePath;
        }

        @Override
        public Texture2D create()
        {
            return new Texture2D(this);
        }

        @Override
        public Class<? extends Texture2D> getResourceClass()
        {
            return Texture2D.class;
        }
    }

    private final Blueprint blueprint;
    private final int width, height; // Pixels
    private final int ID;

    public Texture2D(Blueprint blueprint)
    {
        this.blueprint = blueprint;
        this.ID = OpenGLMemoryManager.createTexture();

        this.bind(0);
        this.setOpenGLParams();

        int[] size = this.load(blueprint.filePath);
        this.width = size[0];
        this.height = size[1];

        this.unbind(0);
    }

    public void bind(int slot)
    {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, this.ID);
    }

    public void unbind(int slot)
    {
        glActiveTexture(GL_TEXTURE0 + slot);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private int[] load(String imagePath)
    {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);
        ByteBuffer pixels = stbi_load(imagePath, width, height, channels, 0);

        assert pixels != null : Log.error("(Texture2D) Could not load texture from file %s.", blueprint.filePath);
        if (pixels == null)
            return null;

        int format = channels.get(0) == 3 ? GL_RGB : GL_RGBA;
        glTexImage2D(GL_TEXTURE_2D, 0, format, width.get(0), height.get(0), 0, format, GL_UNSIGNED_BYTE, pixels);

        stbi_image_free(pixels);

        return new int[] {
                width.get(0), //
                height.get(0)
        };
    }

    private void setOpenGLParams()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getFilePath()
    {
        return blueprint.filePath;
    }

    @Override
    public String toString()
    {
        return blueprint.filePath;
    }

    @Override
    public IResourceBlueprint<? extends Texture2D> getBlueprint()
    {
        return blueprint;
    }
}
