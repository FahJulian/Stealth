package com.github.fahjulian.stealth.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.stealth.AApplication;
import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.graphics.Shader;
import com.github.fahjulian.stealth.graphics.Texture;

/**
 * Utility class to store resources
 */
public final class ResourcePool {
    
    private static Map<String, Shader> shaders = new HashMap<String, Shader>();
    private static Map<String, Texture> textures = new HashMap<String, Texture>();

    private ResourcePool() {
    }

    /**
     * Retrieve a stored shader
     * @param filepath The path to the shader on the system
     * @return The shader, if found, else null
     */
    public static Shader getShader(String filepath) {
        String key = new File(filepath).getAbsolutePath();

        if (!shaders.containsKey(key)) {
            Log.warn("(ResourcePool) Did not find shader %s.", filepath);
            return null; // TODO: Return a placeholder object instead
        }

        return shaders.get(key);
    }

    /**
     * Retrieve a stored texture
     * @param filepath The path to the texture on the system
     * @return The texture, if found, else null
     */
    public static Texture getTexture(String filepath) {
        String key = new File(filepath).getAbsolutePath();

        if (!textures.containsKey(key)) {
            Log.warn("(ResourcePool) Did not find texture %s.", filepath);
            return null; // TODO: Return a placeholder object instead
        }

        return textures.get(key);
    }

    /**
     * Store a shader 
     * @param filepath The path to the shader on the system (Will be used as a key for the shader)
     * @param shader The shader to store
     */
    public static void addShader(String filepath, Shader shader) {
        if (AApplication.get().isRunning())
            Log.warn("(ResourcePool) Resources should not be added during runtime of the game.");
        if (shaders.containsValue(shader))
            Log.warn("(ResourcePool) Shader %s already exists. A shader should only be added once.", shader);

        String key = new File(filepath).getAbsolutePath();
        if (textures.containsKey(key)) {
            Log.warn("(ResourcePool) Can not add shader %s. Shader already exists.", shader);
        }

        shaders.put(key, shader);
    }

    /**
     * Store a shader 
     * @param filepath The path to the shader on the system (Will be used as a key for the texture)
     * @param texture The shader to store
     */
    public static void addTexture(String filepath, Texture texture) {
        if (AApplication.get().isRunning())
            Log.warn("(ResourcePool) Resources should not be added during runtime of the game.");
        if (textures.containsValue(texture))
            Log.warn("(ResourcePool) Texture %s already exists. A texture should only be added once.", texture);

        String key = new File(filepath).getAbsolutePath();
        if (textures.containsKey(key)) {
            Log.warn("(ResourcePool) Can not add texture %s. Texture already exists.", texture);
        }

        textures.put(key, texture);
    }
}
