package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.graphics.opengl.OpenGLMemoryManager;
import com.github.fahjulian.stealth.graphics.opengl.Shader;

public class Renderer2D
{
    private static ColoredModel model;
    private static Shader shader;

    static
    {
        float[] vertices = {
                0.4f, 0.4f, 0.4f, 0.6f, 0.4f, 0.4f, 0.6f, 0.6f, 0.4f, 0.4f, 0.6f, 0.4f
        };

        float[] colors = {
                1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 1.0f
        };

        int[] indices = {
                0, 1, 2, 2, 3, 0
        };

        model = new ColoredModel(vertices, colors, indices);
        shader = new Shader("src/main/resources/shaders/colored_rectangle.glsl");
    }

    /** For now just a normal render method */
    public static void endFrame()
    {
        shader.bind();
        model.bind();

        model.draw();

        model.unbind();
        shader.unbind();
    }

    public static void destroy()
    {
        OpenGLMemoryManager.destroyAll();
    }
}
