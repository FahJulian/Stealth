package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.fahjulian.stealth.entity.component.SpriteComponent;

/** 
 * Groups renderbatches together and manages adding entities to them.
 */
public class Renderer {
    
    private static final int MAX_BATCH_SIZE = 1000;

    private final List<RenderBatch> batches;
    private final String shaderPath;

    /**
     * Construct a new Renderer with a given shader
     * @param shaderPath The path to the shader on the system
     */
    public Renderer(String shaderPath) {
        batches = new ArrayList<>();
        this.shaderPath = shaderPath;
    }

    /**
     * Add a SpriteComponent to the Renderer
     * @param sprite The SpriteComponent to add
     */
    public void add(SpriteComponent sprite) {
        for (RenderBatch batch : batches) {
            if (batch.hasRoomFor(sprite) && sprite.getEntity().getTransform().getZIndex() == batch.getZIndex()) {
                batch.add(sprite);
                return;
            }
        }
        
        RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.getEntity().getTransform().getZIndex(), shaderPath);
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
