package com.github.fahjulian.stealth.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.fahjulian.stealth.entity.component.SpriteComponent;

public class Renderer {
    
    private static final int MAX_BATCH_SIZE = 1000;

    private final List<RenderBatch> batches;
    private final String shaderPath;

    public Renderer(String shaderPath) {
        batches = new ArrayList<>();
        this.shaderPath = shaderPath;
    }

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

    public void render() {
        for (RenderBatch batch : batches)   
            batch.render();
    }
}
