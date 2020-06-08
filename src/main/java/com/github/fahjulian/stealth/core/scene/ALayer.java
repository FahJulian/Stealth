package com.github.fahjulian.stealth.core.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.core.AApplication;
import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.event.AEvent;
import com.github.fahjulian.stealth.core.event.EventManager;
import com.github.fahjulian.stealth.core.event.IEventListener;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.ResourcePool;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Renderer2D;
import com.github.fahjulian.stealth.graphics.Shader;

public abstract class ALayer {
    
    private final List<Entity> entities;
    private int index;
    protected AScene scene;
    private EventManager eventManager;
    private Renderer2D renderer;
    private String shaderPath;
    private boolean initialized;
    private boolean active;

    public ALayer() {
        entities = new ArrayList<>();
        shaderPath = "src/main/java/resources/shaders/default.glsl";
        initialized = false;
    }

    abstract protected void onInit();

    /**
     * Initialize the Layer on the given scene
     * @param scene The scene to initialize the sprite on
     * @param index The index of the layer in the layerstack of the scene
     */
    public void init(AScene scene, int index) {
        this.scene = scene;
        this.index = index;

        eventManager = new EventManager(String.format("EventManager of Layer %d", index));
        AApplication.get().getMainEventManager().addListener(AEvent.class, eventManager::dispatch, this::isActive, index);

        onInit();

        ResourcePool.addShader(shaderPath, new Shader(shaderPath));
        eventManager.addListener(RenderEvent.class, this::onRender, Integer.MAX_VALUE - index);
        renderer = new Renderer2D(shaderPath);
        
        for (Entity e: entities) {
            e.init();
            if (e.hasComponent(SpriteComponent.class))
                renderer.add(e.getComponent(SpriteComponent.class));
        }

        initialized = true;
        active = true;
    }

    /**
     * Add an event listener (mostly in a component on the layer)
     * @param <E> The type of Event the listener listens to
     * @param eventClass The class of the event
     * @param listener The listener to register
     */
    public <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        eventManager.addListener(eventClass, listener, index);
    }

    private boolean onRender(RenderEvent event) {
        renderer.render();
        return false;
    }

    /**
     * Add an Entity to the Layer
     * @param e The Entity to add
     */
    public void add(Entity e) {
        entities.add(e);
        e.setLayer(this);
        if (initialized) e.init();

        if (initialized && e.hasComponent(SpriteComponent.class))
            renderer.add(e.getComponent(SpriteComponent.class));
    }

    /**
     * Set the path to the shader to use in the layers renderer.
     * Should only be called in the onInit() method of the subclass.
     * @param shaderPath The path to the .glsl file on the system
     */
    protected void setShader(String shaderPath) {
        if (initialized) {
            Log.warn("(AShader) Can only class setShader() in onInit() method of ALayer subclass.");
            return;
        }

        this.shaderPath = shaderPath;
    }

    public boolean isActive() {
        return active;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
