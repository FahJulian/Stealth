package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.Log;

import org.joml.Vector2f;

public class Spritesheet extends Texture {
    
    private List<Sprite> sprites;
    private int rows, cols;

    public Spritesheet(String texturePath, int spriteWidth, int spriteHeight, int spacing) {
        super(texturePath);

        this.sprites = new ArrayList<Sprite>();
        this.cols = getWidth() / (spriteWidth + spacing);
        this.rows = getHeight() / (spriteHeight + spacing);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                float x0 = (x * (spriteWidth + spacing)) / (float) getWidth(),             
                      x1 = x0 + (spriteWidth / (float) getWidth()),          
                      y0 = (y * (spriteHeight + spacing)) / (float) getHeight(),
                      y1 = y0 + (spriteHeight / (float) getHeight());

                Vector2f[] coords = new Vector2f[] {
                    new Vector2f(x0, y1),
                    new Vector2f(x1, y1),
                    new Vector2f(x0, y0),
                    new Vector2f(x1, y0)
                };
                sprites.add(new Sprite(this, coords));
            }
        }
    }

    public Spritesheet(String texturePath, int spriteWidth, int spriteHeight) {
        this(texturePath, spriteWidth, spriteHeight, 0);
    }

    public Sprite getSprite(int x, int y) {
        if (x < 0 || x >= cols || y < 0 || y >= rows)
            Log.warn("(Spritesheet) Illegal coordinates for Sprite on Spritesheet %s: (%d, %d)", this, x, y);
        return sprites.get(x + y * cols);
    }
}
