package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.glFlush;

import com.github.fahjulian.stealth.core.AApplication;
import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.graphics.opengl.OpenGLMemoryManager;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Renderer2D
{
    public static final Texture2D PLAYER_TEXTURE;

    private static final int MAX_COLORED_RECTS;
    private static final BatchedColoredModel COLORED_RECTS_MODEL;
    private static final Shader COLORED_RECTS_SHADER;

    private static final int MAX_TEXTURED_RECTS;
    private static final BatchedTexturedModel TEXTURED_RECTS_MODEL;
    private static final Shader TEXTURED_RECTS_SHADER;

    static
    {
        PLAYER_TEXTURE = new Texture2D("src/main/resources/textures/player.png");

        MAX_COLORED_RECTS = 100000;
        COLORED_RECTS_MODEL = new BatchedColoredModel(MAX_COLORED_RECTS);
        COLORED_RECTS_SHADER = new Shader("src/main/resources/shaders/batched_colored_rectangle.glsl");

        MAX_TEXTURED_RECTS = 100000;
        TEXTURED_RECTS_MODEL = new BatchedTexturedModel(MAX_TEXTURED_RECTS);
        TEXTURED_RECTS_SHADER = new Shader("src/main/resources/shaders/batched_textured_rectangle.glsl");
    }

    private Renderer2D()
    {
    }

    public static void drawRectangle(float x, float y, float width, float height, Texture2D texture)
    {
        drawRectangle(x, y, 0.0f, width, height, texture);
    }

    public static void drawRectangle(float x, float y, float z, float width, float height, Texture2D texture)
    {
        TEXTURED_RECTS_MODEL.addRect(x, y, z, width, height);
    }

    public static void drawRectangle(float x, float y, float width, float height, Color color)
    {
        drawRectangle(x, y, 0.0f, width, height, color);
    }

    public static void drawRectangle(float x, float y, float z, float width, float height, Color color)
    {
        COLORED_RECTS_MODEL.addRect(x, y, z, width, height, color);
    }

    public static void startFrame()
    {
        COLORED_RECTS_MODEL.clear();
        TEXTURED_RECTS_MODEL.clear();
    }

    public static void endFrame()
    {
        Camera camera = AApplication.get().getScene().getCamera();
        drawColoredRects(camera);
        drawTexturedRects(camera);
        glFlush();
    }

    public static void destroy()
    {
        OpenGLMemoryManager.destroyAll();
    }

    private static void drawColoredRects(Camera camera)
    {
        COLORED_RECTS_SHADER.bind();
        COLORED_RECTS_MODEL.bind();
        COLORED_RECTS_SHADER.setUniform("uViewMatrix", camera.getViewMatrix());
        COLORED_RECTS_SHADER.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        COLORED_RECTS_MODEL.draw();
        COLORED_RECTS_MODEL.unbind();
        COLORED_RECTS_SHADER.unbind();
    }

    private static void drawTexturedRects(Camera camera)
    {
        TEXTURED_RECTS_SHADER.bind();
        TEXTURED_RECTS_MODEL.bind();
        TEXTURED_RECTS_SHADER.setUniform("uViewMatrix", camera.getViewMatrix());
        TEXTURED_RECTS_SHADER.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        PLAYER_TEXTURE.bind();
        TEXTURED_RECTS_MODEL.draw();
        PLAYER_TEXTURE.unbind();
        TEXTURED_RECTS_MODEL.unbind();
        TEXTURED_RECTS_SHADER.unbind();
    }
}
