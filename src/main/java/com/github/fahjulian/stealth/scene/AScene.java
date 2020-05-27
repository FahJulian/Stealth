package com.github.fahjulian.stealth.scene;

import java.util.List;

/** Just a placeholder for now */
public abstract class AScene {
    protected boolean running;
    protected List<ASceneLayer> layerStack;
    protected Camera camera;

    protected AScene() {
    }

    abstract public void init();
}
