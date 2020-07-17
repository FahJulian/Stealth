package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

/** Hold track of all loaded opengl objects */
public class OpenGLMemoryManager
{
    private static final List<Integer> loadedShaders = new ArrayList<>();
    private static final List<Integer> loadedPrograms = new ArrayList<>();
    private static final List<Integer> loadedVertexArrays = new ArrayList<>();
    private static final List<Integer> loadedVertexBuffers = new ArrayList<>();
    private static final List<Integer> loadedTextures = new ArrayList<>();

    /** Makes opengl free all memory related to loaded objects */
    public static void destroyAll()
    {
        for (int id : loadedShaders)
            glDeleteShader(id);

        for (int id : loadedPrograms)
            glDeleteProgram(id);

        for (int id : loadedVertexArrays)
            glDeleteVertexArrays(id);

        for (int id : loadedVertexBuffers)
            glDeleteBuffers(id);

        for (int id : loadedTextures)
            glDeleteTextures(id);
    }

    static int createShader(int type)
    {
        final int ID = glCreateShader(type);
        loadedShaders.add(ID);
        return ID;
    }

    static int createProgram()
    {
        final int ID = glCreateProgram();
        loadedPrograms.add(ID);
        return ID;
    }

    static int createVertexArray()
    {
        final int ID = glGenVertexArrays();
        loadedVertexArrays.add(ID);
        return ID;
    }

    static int createVertexBuffer()
    {
        final int ID = glGenBuffers();
        loadedVertexBuffers.add(ID);
        return ID;
    }

    static int createTexture()
    {
        final int ID = glGenTextures();
        loadedTextures.add(ID);
        return ID;
    }

    static FloatBuffer toFloatBuffer(float[] data)
    {
        return BufferUtils.createFloatBuffer(data.length).put(data).flip();
    }

    static IntBuffer toIntBuffer(int[] data)
    {
        return BufferUtils.createIntBuffer(data.length).put(data).flip();
    }
}
