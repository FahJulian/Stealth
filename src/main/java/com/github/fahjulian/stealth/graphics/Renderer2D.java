package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.fahjulian.stealth.components.SpriteComponent;

/** 
 * Groups renderbatches together and manages adding entities to them.
 */
public class Renderer2D {
    
    private static final int MAX_BATCH_SIZE = 1000;

    private final List<RenderBatch> batches;
    private final String shaderPath;

    /**
     * Construct a new Renderer with a given shader
     * @param shaderPath The path to the shader on the system
     */
    public Renderer2D(String shaderPath) {
        batches = new ArrayList<>();
        this.shaderPath = shaderPath;
    }

    /**
     * Add a SpriteComponent to the Renderer
     * @param sprite The SpriteComponent to add
     */
    public void add(SpriteComponent sprite) {
        int zIndex = (int)sprite.getEntity().getTransform().getPosition().z;

        for (RenderBatch batch : batches) {
            if (batch.hasRoomFor(sprite) && zIndex == batch.getZIndex()) {
                batch.add(sprite);
                return;
            }
        }
        
        RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, zIndex, shaderPath);
        newBatch.init();
        newBatch.add(sprite);
        batches.add(newBatch);
        Collections.sort(batches);
    }

    /**
     * Draw all SpriteComponents to the Screen
     */
    public void render() {
        for (RenderBatch batch : batches)   
            batch.render();
    }
}
