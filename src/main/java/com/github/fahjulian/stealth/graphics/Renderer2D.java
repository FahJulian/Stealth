package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFlush;

import java.util.Arrays;

import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.opengl.OpenGLMemoryManager;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Renderer2D
{
    static Camera camera;

    static Texture2D[] registeredTextures;
    static int registeredTexturesCount;

    static final int MAX_COLORED_RECTS;
    static BatchedColoredModel coloredRectsModel;
    static Shader coloredRectsShader;

    static final int MAX_TEXTURED_RECTS;
    static BatchedTexturedModel texturedRectsModel;
    static Shader texturedRectsShader;

    private Renderer2D()
    {
    }

    static
    {
        MAX_COLORED_RECTS = 100000;
        MAX_TEXTURED_RECTS = 100000;
        registeredTexturesCount = 0;
        registeredTextures = new Texture2D[16];
    }

    public static void init(Camera camera)
    {
        Renderer2D.camera = camera;

        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        glClearColor(0.2f, 0.2f, 0.2f, 0.2f);

        coloredRectsModel = new BatchedColoredModel(MAX_COLORED_RECTS);
        coloredRectsShader = new Shader("src/main/resources/shaders/batched_colored_rectangle.glsl");

        texturedRectsModel = new BatchedTexturedModel(MAX_TEXTURED_RECTS);
        texturedRectsShader = new Shader("src/main/resources/shaders/batched_textured_rectangle.glsl");

        Log.info("(Renderer2D) Initialized Renderer.");
    }

    public static void drawRectangle(float x, float y, float width, float height, Texture2D texture)
    {
        drawRectangle(x, y, 0.0f, width, height, texture);
    }

    public static void drawRectangle(float x, float y, float z, float width, float height, Texture2D texture)
    {
        int textureSlot = Arrays.asList(registeredTextures).indexOf(texture);
        if (textureSlot == -1)
        {
            Log.warn("(Renderer2D) Could not draw Rectangle with texture %s. Must first register this texture.",
                    texture);
            return;
        }

        texturedRectsModel.addRect(x, y, z, width, height, textureSlot);
    }

    public static void drawRectangle(float x, float y, float width, float height, Color color)
    {
        drawRectangle(x, y, 0.0f, width, height, color);
    }

    public static void drawRectangle(float x, float y, float z, float width, float height, Color color)
    {
        coloredRectsModel.addRect(x, y, z, width, height, color);
    }

    public static void drawTileMap(TileMap2D map)
    {
        map.getModel().bind();
        texturedRectsShader.bind();
        texturedRectsShader.setUniform("uViewMatrix", camera.getViewMatrix());
        texturedRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        map.bindTextures();

        glDrawElements(GL_TRIANGLES, map.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);

        map.unbindTextures();
        map.getModel().unbind();
        texturedRectsShader.unbind();
    }

    public static void startFrame()
    {
        coloredRectsModel.clear();
        texturedRectsModel.clear();
    }

    public static void endFrame()
    {
        drawColoredRects();
        drawTexturedRects();
        glFlush();
    }

    public static void destroy()
    {
        OpenGLMemoryManager.destroyAll();
    }

    public static void registerTexture(Texture2D texture)
    {
        if (registeredTexturesCount == 32)
        {
            Log.warn("(Renderer2D) Can not register texture %s. For now only 16 total Textures are suported.", texture);
            return;
        }

        if (Arrays.asList(registeredTextures).contains(texture))
            return;

        registeredTextures[registeredTexturesCount++] = texture;
    }

    private static void drawColoredRects()
    {
        coloredRectsShader.bind();
        coloredRectsModel.bind();
        coloredRectsShader.setUniform("uViewMatrix", camera.getViewMatrix());
        coloredRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());

        coloredRectsModel.rebuffer();
        glDrawElements(GL_TRIANGLES, coloredRectsModel.getVertexCount(), GL_UNSIGNED_INT, 0);

        coloredRectsModel.unbind();
        coloredRectsShader.unbind();
    }

    private static void drawTexturedRects()
    {
        texturedRectsShader.bind();
        texturedRectsModel.bind();
        texturedRectsShader.setUniform("uViewMatrix", camera.getViewMatrix());
        texturedRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        texturedRectsShader.setUniform("uTextures", new int[] {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
        });

        for (int i = 0; i < registeredTexturesCount; i++)
            registeredTextures[i].bind(i);

        texturedRectsModel.rebuffer();
        glDrawElements(GL_TRIANGLES, texturedRectsModel.getVertexCount(), GL_UNSIGNED_INT, 0);

        for (int i = 0; i < registeredTexturesCount; i++)
            registeredTextures[i].unbind(i);

        texturedRectsModel.unbind();
        texturedRectsShader.unbind();
    }

    public static void setCamera(Camera camera)
    {
        Renderer2D.camera = camera;
    }
}
