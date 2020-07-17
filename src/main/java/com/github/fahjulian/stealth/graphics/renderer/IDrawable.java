package com.github.fahjulian.stealth.graphics.renderer;

import com.github.fahjulian.stealth.core.scene.Camera;

/**
 * Represents an object that can draw itself and does not need the Renderer to
 * do so.
 */
public interface IDrawable
{
    /**
     * Draws the object to the screen
     * 
     * @param camera
     *                   The currently used camera
     */
    void draw(Camera camera);
}
