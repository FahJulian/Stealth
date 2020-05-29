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
import com.github.fahjulian.stealth.graphics.Shader;
import com.github.fahjulian.stealth.util.ResourcePool;

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

    public void init(AScene scene, int index) {
        this.scene = scene;
        this.index = index;

        onInit();

        ResourcePool.addShader(shaderPath, new Shader(shaderPath));
        EventManager.addListener(RenderEvent.class, this::onRender, Integer.MAX_VALUE - index);
        renderer = new Renderer(shaderPath);
        
        for (Entity e: entities) {
            e.init();
            if (e.hasComponent(SpriteComponent.class))
                renderer.add(e.getComponent(SpriteComponent.class));
        }

        initialized = true;
    }

    public <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        EventManager.addListener(eventClass, listener, index);
    }

    public boolean onRender(RenderEvent event) {
        renderer.render();
        return false;
    }

    public void add(Entity e) {
        entities.add(e);
        e.setLayer(this);
        if (initialized) e.init();

        if (initialized && e.hasComponent(SpriteComponent.class))
            renderer.add(e.getComponent(SpriteComponent.class));
    }

    protected void setShader(String shaderPath) {
        if (initialized) {
            Log.warn("(AShader) Can only class setShader() in onInit() method of ALayer subclass.");
            return;
        }

        this.shaderPath = shaderPath;
    }
}
