package com.github.fahjulian.stealth.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.Entity;
import com.github.fahjulian.stealth.entity.component.SpriteComponent;
import com.github.fahjulian.stealth.event.AEvent;
import com.github.fahjulian.stealth.event.EventManager;
import com.github.fahjulian.stealth.event.IEventListener;
import com.github.fahjulian.stealth.event.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Renderer;

public abstract class ALayer {
    
    private int index;
    protected AScene scene;
    private final List<Entity> entities;
    private Renderer renderer;
    private String shaderPath;
    private boolean initialized;

    public ALayer() {
        entities = new ArrayList<>();
        shaderPath = "src/main/java/resources/shaders/default.glsl";
        initialized = false;
    }

    abstract public void onInit();

    public <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        EventManager.addListener(eventClass, listener, index);
    }

    void setScene(AScene scene, int index) {
        this.scene = scene;
        this.index = index;
    }

    public boolean onRender(RenderEvent event) {
        renderer.render();
        return false;
    }

    public void init() {
        if (scene == null) {
            Log.warn("Scene must be set for layer to be initialized.");
            return;
        }

        onInit();

        for (Entity e: entities)
            e.init();

        renderer = new Renderer(shaderPath);
        EventManager.addListener(RenderEvent.class, this::onRender, Integer.MAX_VALUE - index);
        initialized = true;
    }

    public void add(Entity e) {
        entities.add(e);
        e.setLayer(this);

        if (e.hasComponent(SpriteComponent.class))
            renderer.add(e.getComponent(SpriteComponent.class));
    }

    protected void setShader(String shaderPath) {
        if (initialized) {
            Log.warn("(AShader) Can only set shader path of a layer before or during initialization, not after.");
            return;
        }

        this.shaderPath = shaderPath;
    }
}
