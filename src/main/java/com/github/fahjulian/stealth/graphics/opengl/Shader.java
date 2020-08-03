package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1iv;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.util.Map;

import com.github.fahjulian.stealth.core.resources.Deserializer;
import com.github.fahjulian.stealth.core.resources.ISerializable;
import com.github.fahjulian.stealth.core.util.IO;
import com.github.fahjulian.stealth.core.util.Log;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

/** Wrapper for an opengl shader program */
// TODO: Scan source code for unimors and create a hashmap when loading
public class Shader implements ISerializable
{
    private int ID;
    private final String filePath;

    /**
     * Construct a new shader from a .glsl file that contains both the vertex shader
     * and the fragment shader source code. The vertex shader must start with the
     * line '# type vertex', the fragment shader must start with the line '# type
     * fragment'
     * 
     * @param filePath
     *                     Path to the .glsl file
     */
    public Shader(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    public void load()
    {
        final String[] splitCode = splitSourceCode(IO.loadResource(filePath));
        assert splitCode != null : Log
                .error("(Shader) Could not load shader %s. Please check shader type declerations.'", filePath);

        int vertexID = createShader(GL_VERTEX_SHADER, splitCode[0]);
        int fragmentID = createShader(GL_FRAGMENT_SHADER, splitCode[1]);
        this.ID = linkToProgram(vertexID, fragmentID);

        assert loadingSuccessful(ID, vertexID, fragmentID) : Log.error("(Shader) Failed to compile or link Shader %s",
                filePath);
    }

    /** Binds the shader program to the graphics card */
    public void bind()
    {
        glUseProgram(this.ID);
    }

    /** Unbind the shader program from the graphics card */
    public void unbind()
    {
        glUseProgram(0);
    }

    public void setUniform(String uniformName, Vector4f value)
    {
        int location = glGetUniformLocation(this.ID, uniformName);
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void setUniform(String uniformName, Matrix4f value)
    {
        int location = glGetUniformLocation(this.ID, uniformName);
        glUniformMatrix4fv(location, false, value.get(BufferUtils.createFloatBuffer(4 * 4)));
    }

    public void setUniform(String uniformName, float value)
    {
        int location = glGetUniformLocation(this.ID, uniformName);
        glUniform1f(location, value);
    }

    public void setUniform(String uniformName, int[] value)
    {
        int location = glGetUniformLocation(this.ID, uniformName);
        glUniform1iv(location, value);
    }

    private String[] splitSourceCode(String sourceCode)
    {
        int vertexStart = sourceCode.indexOf("# type vertex");
        int fragmentStart = sourceCode.indexOf("# type fragment");

        if (vertexStart == -1 || fragmentStart == -1 || vertexStart > fragmentStart)
            return null;

        return new String[] {
                sourceCode.substring(vertexStart + "# type vertex".length(), fragmentStart),
                sourceCode.substring(fragmentStart + "# type fragment".length())
        };
    }

    private int createShader(int type, String sourceCode)
    {
        final int ID = OpenGLMemoryManager.createShader(type);
        glShaderSource(ID, sourceCode);
        glCompileShader(ID);
        return ID;
    }

    private int linkToProgram(int vertexID, int fragmentID)
    {
        final int ID = OpenGLMemoryManager.createProgram();
        bind();
        glAttachShader(ID, vertexID);
        glAttachShader(ID, fragmentID);
        glLinkProgram(ID);
        unbind();
        return ID;
    }

    private boolean loadingSuccessful(int programID, int vertexID, int fragmentID)
    {
        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            Log.error(glGetShaderInfoLog(vertexID));
            return false;
        }
        else if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            Log.error(glGetShaderInfoLog(fragmentID));
            return false;
        }
        else if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
        {
            Log.error(glGetProgramInfoLog(programID));
            return false;
        }

        return true;
    }

    @Override
    public String getUniqueKey()
    {
        return filePath;
    }

    @Override
    public void serialize(Map<String, Object> fields)
    {
        fields.put("filePath", filePath);
    }

    @Deserializer
    public static Shader deserialize(Map<String, String> fields)
    {
        return new Shader(fields.get("filePath"));
    }
}
