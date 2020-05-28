package com.github.fahjulian.stealth.scene;

import java.util.ArrayList;
import java.util.List;

/** Just a placeholder for now */
public abstract class AScene {

    protected boolean running;
    protected List<ALayer> layerStack;
    protected Camera camera;
    private boolean initialized;

    protected AScene() {
        layerStack = new ArrayList<>();
        initialized = false;
    }

    public abstract void onInit();

    public void init() {
        onInit();

        for (ALayer layer : layerStack) 
            layer.init();
        
        initialized = true;
    }

    public void add(ALayer layer) {
        layerStack.add(layer);
        layer.setScene(this, layerStack.indexOf(layer));
        if (initialized) layer.init();
    }

    public Camera getCamera() {
        return camera;
    }
}
