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

    abstract protected void onInit();

    /** Initialize the scene. Should only be called by {@link com.github.fahjulian.stealth.AApplication} */
    public void init() {
        onInit();

        for (ALayer layer : layerStack) 
            layer.init(this, layerStack.indexOf(layer));
        
        initialized = true;
    }

    /** 
     * Add a layer to the scene
     * @param layer The layer to add
     */
    public void add(ALayer layer) {
        layerStack.add(layer);
        if (initialized) layer.init(this, layerStack.indexOf(layer));
    }

    public Camera getCamera() {
        return camera;
    }
}
