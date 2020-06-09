package com.github.fahjulian.stealth.graphics.opengl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import com.github.fahjulian.stealth.core.util.IO;
import com.github.fahjulian.stealth.core.util.Log;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class Shader
{
    private final int ID;

    public Shader(String glslPath)
    {
        String sourceCode = IO.loadResource(glslPath);

        String[] splitCode = splitSourceCode(sourceCode);
        if (splitCode == null)
        {
            Log.error("(Shader) Could not load shader %s. Please check shader type declerations.'",
                    glslPath);
            this.ID = 0;
            return;
        }

        int vertexID = createShader(GL_VERTEX_SHADER, splitCode[0]);
        int fragmentID = createShader(GL_FRAGMENT_SHADER, splitCode[1]);

        this.ID = linkToProgram(vertexID, fragmentID);
        if (!checkForErrors(this.ID, vertexID, fragmentID))
        {
            Log.error("(Shader) Failed to compile or link Shader %s", glslPath);
            return;
        }
    }

    public Shader(String vertexPath, String fragmentPath)
    {
        int vertexID = createShader(GL_VERTEX_SHADER, IO.loadResource(vertexPath));
        int fragmentID = createShader(GL_FRAGMENT_SHADER, IO.loadResource(fragmentPath));

        this.ID = linkToProgram(vertexID, fragmentID);
        if (!checkForErrors(this.ID, vertexID, fragmentID))
        {
            Log.error("(Shader) Failed to compile or link Shader with files %s and %s", vertexPath,
                    fragmentPath);
        }
    }

    public void bind()
    {
        glUseProgram(this.ID);
    }

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
        int ID = glCreateShader(type);
        glShaderSource(ID, sourceCode);
        glCompileShader(ID);
        OpenGLMemoryManager.loadedShaders.add(ID);
        return ID;
    }

    private int linkToProgram(int vertexID, int fragmentID)
    {
        int ID = glCreateProgram();
        glAttachShader(ID, vertexID);
        glAttachShader(ID, fragmentID);
        glLinkProgram(ID);
        OpenGLMemoryManager.loadedPrograms.add(ID);
        return ID;
    }

    private boolean checkForErrors(int programID, int vertexID, int fragmentID)
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
}
