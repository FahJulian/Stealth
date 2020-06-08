package com.github.fahjulian.stealth.graphics;

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
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1iv;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.nio.FloatBuffer;

import com.github.fahjulian.stealth.core.util.IO;
import com.github.fahjulian.stealth.core.util.Log;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;

public class Shader {

	private int ID;
	private String vertexSource;
	private String fragmentSource;

	/** 
	 * Construct a new Shader from a .glsl file 
	 * @param filePath The path to the .glsl file on the system
	 */
	public Shader(String filePath) { 
		try {
			loadSource(filePath);
			compile();
		} catch (Exception ex) {
			Log.error("(Shader) Error loading shader %s", filePath);
			return;
		}
	}

	private void loadSource(String filePath) throws Exception {
		final String source = IO.loadResource(filePath);
		
		String[] splitSource = source.split("(#type)|(# type)( )+([a-zA-Z]+)");

		int index = (source.indexOf("#type") != -1) ? source.indexOf("#type") + 6 : source.indexOf("# type") + 7;
		int eol = (source.indexOf("\n", index) != -1) ? source.indexOf("\n", index) : source.indexOf("\r\n", index);
		String firstPattern = source.substring(index, eol).trim();
		
		index = (source.indexOf("#type", eol) != -1) ? source.indexOf("#type", eol) + 6 : source.indexOf("# type", eol) + 7;
		eol = (source.indexOf("\n", index) != -1) ? source.indexOf("\n", index) : source.indexOf("\r\n", index);
		String secondPattern = source.substring(index, eol).trim();

		switch (firstPattern + secondPattern) {
			case "vertexfragment":
				vertexSource = splitSource[1];
				fragmentSource = splitSource[2];
				break;
			case "fragmentvertex":
				fragmentSource = splitSource[1];
				vertexSource = splitSource[2];	
				break;
			default:
				Log.error("(Shader) Unexpected token: " + secondPattern);
				throw new Exception();
		}
	}

	private void compile() throws Exception {
		int vertexID = glCreateShader(GL_VERTEX_SHADER);
		int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertexID, vertexSource);
		glShaderSource(fragmentID, fragmentSource);
		glCompileShader(vertexID);
		glCompileShader(fragmentID);

		if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
			Log.error(glGetShaderInfoLog(vertexID));
			throw new Exception();
		}
		else if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
			Log.error(glGetShaderInfoLog(fragmentID));
			throw new Exception();
		}

		this.ID = glCreateProgram();
		glAttachShader(this.ID, vertexID);
		glAttachShader(this.ID, fragmentID);
		glLinkProgram(this.ID);

		if (glGetProgrami(this.ID, GL_LINK_STATUS) == GL_FALSE) {
			Log.error(glGetProgramInfoLog(this.ID));
			throw new Exception();
		}
	}

	public void bind() {
		glUseProgram(this.ID);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public void upload(String varName, Matrix4f matrix) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        matrix.get(matrixBuffer);
        glUniformMatrix4fv(varLocation, false, matrixBuffer);
    }

    public void upload(String varName, Matrix3f matrix) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(9);
        matrix.get(matrixBuffer);
        glUniformMatrix3fv(varLocation, false, matrixBuffer);
    }

    public void upload(String varName, Vector4f vector) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform4f(varLocation, vector.x, vector.y, vector.z, vector.w);
    }

    public void upload(String varName, Vector3f vector) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform3f(varLocation, vector.x, vector.y, vector.z);
    }

    public void upload(String varName, Vector2f vector) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform2f(varLocation, vector.x, vector.y);
    }

    public void upload(String varName, float value) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform1f(varLocation, value);
    }

    public void upload(String varName, Vector4i vector) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform4i(varLocation, vector.x, vector.y, vector.z, vector.w);
    }

    public void upload(String varName, Vector3i vector) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform3i(varLocation, vector.x, vector.y, vector.z);
    }

    public void upload(String varName, Vector2i vector) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform2i(varLocation, vector.x, vector.y);
    }

    public void upload(String varName, int value) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform1i(varLocation, value);
    }

    public void upload(String varName, int[] values) {
        bind();
        int varLocation = glGetUniformLocation(this.ID, varName);
        glUniform1iv(varLocation, values);
    }
}
