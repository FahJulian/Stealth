package com.github.fahjulian.stealth.graphics;

import org.joml.Vector2f;

public class Sprite {

	private final Spritesheet spritesheet;
	private final Vector2f[] spritesheetCoords;

	public Sprite(Spritesheet spritesheet, Vector2f[] spritesheetCoords) {
		this.spritesheet = spritesheet;
		this.spritesheetCoords = spritesheetCoords;
	}

	public Texture getTexture() {
		return spritesheet;
    }
    
	public Vector2f[] getTextureCoords() {
		return spritesheetCoords;
	}
    
}
