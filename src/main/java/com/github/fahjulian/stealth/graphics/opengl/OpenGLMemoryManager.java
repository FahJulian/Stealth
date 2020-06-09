package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

import java.util.ArrayList;
import java.util.List;

public class OpenGLMemoryManager
{

    static final List<Integer> loadedShaders = new ArrayList<>();
    static final List<Integer> loadedPrograms = new ArrayList<>();
    static final List<Integer> loadedVertexArrays = new ArrayList<>();
    static final List<Integer> loadedVertexBuffers = new ArrayList<>();

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
    }
}
