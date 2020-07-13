package com.github.fahjulian.stealth.graphics.renderer;

import static org.lwjgl.opengl.GL11.glFlush;

import java.util.Arrays;

import com.github.fahjulian.stealth.core.scene.Camera;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.opengl.OpenGLMemoryManager;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;
import com.github.fahjulian.stealth.graphics.tilemap.TileMap;

public class Renderer2D
{
    static Camera camera;

    static Texture2D[] registeredTextures;
    static int[] textureSlots;
    static int registeredTexturesCount;

    static final int MAX_COLORED_RECTS;
    static BatchedColoredModel coloredRectsModel;
    static Shader coloredRectsShader;

    static final int MAX_TEXTURED_RECTS;
    static BatchedTexturedModel texturedRectsModel;
    static Shader texturedRectsShader;

    static final int MAX_STATIC_COLORED_RECTS;
    static BatchedColoredModel staticColoredRectsModel;
    static Shader staticColoredRectsShader;

    static final int MAX_STATIC_TEXTURED_RECTS;
    static BatchedTexturedModel staticTexturedRectsModel;
    static Shader staticTexturedRectsShader;

    private Renderer2D()
    {
    }

    static
    {
        MAX_COLORED_RECTS = 20000;
        MAX_TEXTURED_RECTS = 20000;
        MAX_STATIC_COLORED_RECTS = 1000;
        MAX_STATIC_TEXTURED_RECTS = 1000;
        registeredTexturesCount = 0;
        registeredTextures = new Texture2D[16];
        textureSlots = new int[] {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
        };
    }

    public static void init(Camera camera)
    {
        Renderer2D.camera = camera;

        staticColoredRectsModel = new BatchedColoredModel(MAX_STATIC_COLORED_RECTS);
        staticColoredRectsShader = new Shader(
                "/home/julian/dev/java/Stealth/src/main/resources/shaders/static_batched_colored_rectangle.glsl");

        staticTexturedRectsModel = new BatchedTexturedModel(MAX_STATIC_TEXTURED_RECTS);
        staticTexturedRectsShader = new Shader(
                "/home/julian/dev/java/Stealth/src/main/resources/shaders/static_batched_textured_rectangle.glsl");

        coloredRectsModel = new BatchedColoredModel(MAX_COLORED_RECTS);
        coloredRectsShader = new Shader(
                "/home/julian/dev/java/Stealth/src/main/resources/shaders/batched_colored_rectangle.glsl");

        texturedRectsModel = new BatchedTexturedModel(MAX_TEXTURED_RECTS);
        texturedRectsShader = new Shader(
                "/home/julian/dev/java/Stealth/src/main/resources/shaders/batched_textured_rectangle.glsl");

        Log.info("(Renderer2D) Initialized Renderer.");
    }

    public static void drawStaticRectangle(float x, float y, float width, float height, Color color)
    {
        drawStaticRectangle(x, y, 0.0f, width, height, color);
    }

    public static void drawStaticRectangle(float x, float y, float z, float width, float height, Color color)
    {
        staticColoredRectsModel.addRect(x, y, z, width, height, color);
    }

    public static void drawStaticRectangle(float x, float y, float width, float height, Sprite sprite)
    {
        drawStaticRectangle(x, y, 0.0f, width, height, sprite);
    }

    public static void drawStaticRectangle(float x, float y, float z, float width, float height, Sprite sprite)
    {
        int textureSlot = Arrays.asList(registeredTextures).indexOf(sprite.getTexture());
        if (textureSlot == -1)
        {
            Log.warn("(Renderer2D) Could not draw Rectangle with texture %s. Must first register this texture.",
                    sprite.getTexture());
            return;
        }

        staticTexturedRectsModel.addRect(x, y, z, width, height, sprite.getTextureCoords(), textureSlot);
    }

    public static void drawRectangle(float x, float y, float width, float height, Sprite sprite)
    {
        drawRectangle(x, y, 0.0f, width, height, sprite);
    }

    public static void drawRectangle(float x, float y, float z, float width, float height, Sprite sprite)
    {
        int textureSlot = Arrays.asList(registeredTextures).indexOf(sprite.getTexture());
        if (textureSlot == -1)
        {
            Log.warn("(Renderer2D) Could not draw Rectangle with texture %s. Must first register this texture.",
                    sprite.getTexture());
            return;
        }

        texturedRectsModel.addRect(x, y, z, width, height, sprite.getTextureCoords(), textureSlot);
    }

    public static void drawRectangle(float x, float y, float width, float height, Color color)
    {
        drawRectangle(x, y, 0.0f, width, height, color);
    }

    public static void drawRectangle(float x, float y, float z, float width, float height, Color color)
    {
        coloredRectsModel.addRect(x, y, z, width, height, color);
    }

    public static void drawTileMap(TileMap map)
    {
        texturedRectsShader.bind();
        texturedRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        texturedRectsShader.setUniform("uViewMatrix", camera.getViewMatrix());
        texturedRectsShader.setUniform("uTextures", textureSlots);

        map.getModel().draw();

        texturedRectsShader.unbind();
    }

    public static void startFrame()
    {
        staticColoredRectsModel.clear();
        staticTexturedRectsModel.clear();
        coloredRectsModel.clear();
        texturedRectsModel.clear();
    }

    public static void endFrame()
    {
        drawColoredRects();
        drawTexturedRects();
        drawStaticColoredRects();
        drawStaticTexturedRects();
        glFlush();
    }

    public static void destroy()
    {
        OpenGLMemoryManager.destroyAll();
    }

    public static void registerTexture(Texture2D texture)
    {
        if (registeredTexturesCount == 16)
        {
            Log.warn("(Renderer2D) Can not register texture %s. For now only 16 total Textures are suported.", texture);
            return;
        }

        if (Arrays.asList(registeredTextures).contains(texture))
            return;

        registeredTextures[registeredTexturesCount++] = texture;
    }

    public static void drawModel(AbstractModel model)
    {
        model.draw();
    }

    private static void drawStaticColoredRects()
    {
        if (staticColoredRectsModel.getRectCount() == 0)
            return;

        staticColoredRectsModel.rebuffer();
        staticColoredRectsShader.bind();
        staticColoredRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());

        staticColoredRectsModel.draw();

        staticColoredRectsShader.unbind();
    }

    private static void drawStaticTexturedRects()
    {
        staticTexturedRectsModel.rebuffer();
        staticTexturedRectsShader.bind();
        staticTexturedRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        staticTexturedRectsShader.setUniform("uTextures", textureSlots);

        for (int i = 0; i < registeredTexturesCount; i++)
            registeredTextures[i].bind(i);

        staticTexturedRectsModel.draw();

        for (int i = 0; i < registeredTexturesCount; i++)
            registeredTextures[i].unbind(i);

        staticTexturedRectsShader.unbind();
    }

    private static void drawColoredRects()
    {
        if (coloredRectsModel.getRectCount() == 0)
            return;

        coloredRectsModel.rebuffer();
        coloredRectsShader.bind();
        coloredRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        coloredRectsShader.setUniform("uViewMatrix", camera.getViewMatrix());

        coloredRectsModel.draw();

        coloredRectsShader.unbind();
    }

    private static void drawTexturedRects()
    {
        texturedRectsModel.rebuffer();
        texturedRectsShader.bind();
        texturedRectsShader.setUniform("uProjectionMatrix", camera.getProjectionMatrix());
        texturedRectsShader.setUniform("uViewMatrix", camera.getViewMatrix());
        texturedRectsShader.setUniform("uTextures", textureSlots);

        for (int i = 0; i < registeredTexturesCount; i++)
            registeredTextures[i].bind(i);

        texturedRectsModel.draw();

        for (int i = 0; i < registeredTexturesCount; i++)
            registeredTextures[i].unbind(i);

        texturedRectsShader.unbind();
    }

    public static void setCamera(Camera camera)
    {
        Renderer2D.camera = camera;
    }
}
