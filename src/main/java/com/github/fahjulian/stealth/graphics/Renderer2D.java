package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.glFlush;

import com.github.fahjulian.stealth.core.AApplication;
import com.github.fahjulian.stealth.core.util.Maths;
import com.github.fahjulian.stealth.graphics.opengl.OpenGLMemoryManager;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

import org.joml.Vector3f;

public class Renderer2D
{
    private static final TexturedModel RECTANGLE_MODEL;
    private static final Shader RECTANGLE_SHADER;
    public static final Texture2D PLAYER_TEXTURE;

    static
    {
        float[] positions = {
                -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
        };

        float[] textureCoords = {
                1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f
        };

        int[] indices = {
                0, 1, 2, 2, 3, 0
        };

        RECTANGLE_MODEL = new TexturedModel(positions, textureCoords, indices);
        RECTANGLE_SHADER = new Shader("src/main/resources/shaders/textured_rectangle.glsl");

        PLAYER_TEXTURE = new Texture2D("src/main/resources/textures/player.png");
    }

    private Renderer2D()
    {
    }

    public static void drawRectangle(float x, float y, float width, float height, Color color)
    {
        drawRectangle(new Vector3f(x, y, 0.0f), new Vector3f(width, height, 1.0f), new Vector3f(), color);
    }

    public static void drawRectangle(float x, float y, float width, float height, float rotation, Color color)
    {
        drawRectangle(new Vector3f(x, y, 0.0f), new Vector3f(width, height, 1.0f), new Vector3f(0.0f, 0.0f, rotation),
                color);
    }

    public static void drawTexturedRectangle(float x, float y, float width, float height, Texture2D texture)
    {
        drawTexturedRectangle(new Vector3f(x, y, 0.0f), new Vector3f(width, height, 1.0f), new Vector3f(), texture);
    }

    public static void drawTexturedRectangle(float x, float y, float width, float height, float rotation,
            Texture2D texture)
    {
        drawTexturedRectangle(new Vector3f(x, y, 0.0f), new Vector3f(width, height, 1.0f),
                new Vector3f(0.0f, 0.0f, rotation), texture);
    }

    public static void startFrame()
    {
        RECTANGLE_SHADER.bind();
        RECTANGLE_MODEL.bind();
        RECTANGLE_SHADER.setUniform("uViewMatrix", AApplication.get().getScene().getCamera().getViewMatrix());
        RECTANGLE_SHADER.setUniform("uProjectionMatrix",
                AApplication.get().getScene().getCamera().getProjectionMatrix());
    }

    public static void endFrame()
    {
        RECTANGLE_MODEL.unbind();
        RECTANGLE_SHADER.unbind();
        glFlush();
    }

    private static void drawTexturedRectangle(Vector3f position, Vector3f size, Vector3f rotation, Texture2D texture)
    {
        position.x += size.x / 2.0f;
        position.y += size.y / 2.0f;
        position.z += size.z / 2.0f;

        texture.bind();

        RECTANGLE_SHADER.setUniform("uIsTextured", 1.0f);
        RECTANGLE_SHADER.setUniform("uModelMatrix", Maths.createTransformationMatrix(position, size, rotation));

        RECTANGLE_MODEL.draw();

        texture.unbind();
    }

    private static void drawRectangle(Vector3f position, Vector3f size, Vector3f rotation, Color color)
    {
        position.x += size.x / 2.0f;
        position.y += size.y / 2.0f;
        position.z += size.z / 2.0f;

        RECTANGLE_SHADER.setUniform("uIsTextured", 0.0f);
        RECTANGLE_SHADER.setUniform("uColor", color);
        RECTANGLE_SHADER.setUniform("uModelMatrix", Maths.createTransformationMatrix(position, size, rotation));

        RECTANGLE_MODEL.draw();
    }

    public static void destroy()
    {
        OpenGLMemoryManager.destroyAll();
    }
}
