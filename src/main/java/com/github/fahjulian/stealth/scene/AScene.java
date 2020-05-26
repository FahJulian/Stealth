package com.github.fahjulian.stealth.scene;

import java.util.List;

import com.github.fahjulian.stealth.event.application.RenderEvent;
import com.github.fahjulian.stealth.event.application.UpdateEvent;

/** Just a placeholder for now */
public abstract class AScene {
    protected boolean running;
    protected List<ASceneLayer> layerStack;
    protected Camera camera;

    protected AScene() {
    }

    public boolean onRender(RenderEvent event) {
        for (int i = 0; i < layerStack.size())
    }

    public boolean onUpdate(UpdateEvent event) {
        
    }
}
