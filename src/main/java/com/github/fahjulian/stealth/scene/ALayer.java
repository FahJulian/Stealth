package com.github.fahjulian.stealth.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.entity.Entity;
import com.github.fahjulian.stealth.event.AEvent;
import com.github.fahjulian.stealth.event.EventManager;
import com.github.fahjulian.stealth.event.IEventListener;
import com.github.fahjulian.stealth.event.application.RenderEvent;

public abstract class ALayer {
    
    private int index;
    protected AScene scene;
    private List<Entity> entities;

    public ALayer() {
        entities = new ArrayList<>();
    }

    abstract public boolean onRender(RenderEvent event);
    abstract public void onInit();

    public <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        EventManager.addListener(eventClass, listener, index);
    }

    void setScene(AScene scene, int index) {
        this.scene = scene;
        this.index = index;
        EventManager.addListener(RenderEvent.class, this::onRender, Integer.MAX_VALUE - index);
    }

    public void init() {
        onInit();

        for (Entity e: entities)
            e.init();
    }

    public void add(Entity e) {
        entities.add(e);
        e.setLayer(this);
    }
}
