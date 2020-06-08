package com.github.fahjulian.stealth.components;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.events.entity.EntityTransformEvent;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.Texture;

import org.joml.Vector2f;

public class SpriteComponent extends AComponent {
    
    private Sprite sprite;
    private boolean hasChanges;

    public SpriteComponent(Sprite sprite) {
        this.sprite = sprite;
        this.hasChanges = true;
    }

	@Override
	public void onInit() {
        addEventListener(EntityTransformEvent.class, this::onTransform);
    }

    public boolean onTransform(EntityTransformEvent event) {
        this.hasChanges = true;
        return false;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        hasChanges = true;
    }

    public Vector2f[] getTextureCoords() {
        return sprite.getTextureCoords();
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public boolean hasChanges() {
        return hasChanges;
    }

    public void cleanChanges() {
        hasChanges = false;
    }
}
