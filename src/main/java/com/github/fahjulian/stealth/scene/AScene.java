package com.github.fahjulian.stealth.scene;

import java.util.ArrayList;
import java.util.List;

public abstract class AScene {

    protected List<ALayer> layerStack;
    protected Camera camera;
    private boolean initialized;

    protected AScene() {
        layerStack = new ArrayList<>();
        camera = new Camera(0, 0);
        initialized = false;
    }

    public abstract void onInit();

    public void init() {
        onInit();

        for (ALayer layer : layerStack) 
            layer.init(this, layerStack.indexOf(layer));
        
        initialized = true;
    }

    public void add(ALayer layer) {
        layerStack.add(layer);
        if (initialized) layer.init(this, layerStack.indexOf(layer));
    }

    public Camera getCamera() {
        return camera;
    }
}
