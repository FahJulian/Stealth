package com.github.fahjulian.stealth.entity.component;

import com.github.fahjulian.stealth.entity.Transform;
import com.github.fahjulian.stealth.event.application.UpdateEvent;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.Texture;

import org.joml.Vector2f;

public class SpriteComponent extends AComponent {
    
    private final Sprite sprite;
    private Transform lastTransform;
    private boolean hasChanges;

    public SpriteComponent(Sprite sprite) {
        this.sprite = sprite;
        lastTransform = new Transform();
    }

	@Override
	public void onInit() {
        addEventListener(UpdateEvent.class, this::onUpdate);
    }

	@Override
	public String toString() {
        return String.format("Sprite component of entity %s", entity);
    }

    public boolean onUpdate(UpdateEvent event) {
        if (!lastTransform.equals(entity.getTransform())) {
            hasChanges = true;
            lastTransform = entity.getTransform().clone();
        }

        return false;
    }
    
    public Sprite getSprite() {
        return sprite;
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
