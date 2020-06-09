package com.github.fahjulian.stealth.graphics;

import com.github.fahjulian.stealth.core.AApplication;
import com.github.fahjulian.stealth.core.util.Maths;
import com.github.fahjulian.stealth.graphics.opengl.OpenGLMemoryManager;
import com.github.fahjulian.stealth.graphics.opengl.Shader;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Renderer2D
{
    private static final RawModel RECTANGLE_MODEL;
    private static final TexturedModel TEXTURED_RECTANGLE_MODEL;
    private static final Shader RECTANGLE_SHADER;
    private static final Shader TEXTURED_RECTANGLE_SHADER;
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

        RECTANGLE_MODEL = new RawModel(positions, indices);
        RECTANGLE_SHADER = new Shader("src/main/resources/shaders/basic_rectangle.glsl");
        TEXTURED_RECTANGLE_MODEL = new TexturedModel(positions, textureCoords, indices);
        TEXTURED_RECTANGLE_SHADER = new Shader(
                "src/main/resources/shaders/textured_rectangle.glsl");

        PLAYER_TEXTURE = new Texture2D("src/main/resources/textures/player.png");
    }

    public static void drawRectangle(Vector2f position, Vector2f size, Vector4f color)
    {
        drawRectangle(new Vector3f(position, 0.0f), new Vector3f(size, 1.0f), new Vector3f(),
                color);
    }

    public static void drawRectangle(Vector2f position, Vector2f size, float rotation,
            Vector4f color)
    {
        drawRectangle(new Vector3f(position, 0.0f), new Vector3f(size, 1.0f),
                new Vector3f(0.0f, 0.0f, rotation), color);
    }

    public static void drawTexturedRectangle(float x, float y, float width, float height)
    {
        drawTexturedRectangle(new Vector3f(x, y, 0.0f), new Vector3f(width, height, 1.0f),
                new Vector3f(), PLAYER_TEXTURE);
    }

    public static void drawTexturedRectangle(Vector3f position, Vector3f size, Vector3f rotation,
            Texture2D texture)
    {
        position = new Vector3f(position.x + size.x / 2.0f, position.y + size.y / 2.0f,
                position.z + size.z / 2.0f);

        TEXTURED_RECTANGLE_SHADER.bind();
        TEXTURED_RECTANGLE_MODEL.bind();
        texture.bind();

        TEXTURED_RECTANGLE_SHADER.setUniform("uProjectionMatrix",
                AApplication.get().getScene().getCamera().getProjectionMatrix());
        TEXTURED_RECTANGLE_SHADER.setUniform("uModelMatrix",
                Maths.createTransformationMatrix(position, size, rotation));

        TEXTURED_RECTANGLE_MODEL.draw();

        texture.unbind();
        TEXTURED_RECTANGLE_MODEL.unbind();
        TEXTURED_RECTANGLE_SHADER.unbind();
    }

    public static void drawRectangle(Vector3f position, Vector3f size, Vector3f rotation,
            Vector4f color)
    {
        position = new Vector3f(position.x + size.x / 2.0f, position.y + size.y / 2.0f,
                position.z + size.z / 2.0f);

        RECTANGLE_SHADER.bind();
        RECTANGLE_MODEL.bind();

        RECTANGLE_SHADER.setUniform("uProjectionMatrix",
                AApplication.get().getScene().getCamera().getProjectionMatrix());
        RECTANGLE_SHADER.setUniform("uColor", color);

        TEXTURED_RECTANGLE_SHADER.setUniform("uModelMatrix",
                Maths.createTransformationMatrix(position, size, rotation));

        RECTANGLE_MODEL.draw();

        RECTANGLE_MODEL.unbind();
        RECTANGLE_SHADER.unbind();
    }

    public static void destroy()
    {
        OpenGLMemoryManager.destroyAll();
    }
}
