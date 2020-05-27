package com.github.fahjulian.stealth.scene;

import com.github.fahjulian.stealth.event.AEvent;
import com.github.fahjulian.stealth.event.EventManager;
import com.github.fahjulian.stealth.event.IEventListener;
import com.github.fahjulian.stealth.event.application.RenderEvent;

public abstract class ASceneLayer {
    
    private int index;
    protected AScene scene;

    abstract public boolean onRender(RenderEvent event);
    abstract public void onInit();

    protected <E extends AEvent> void addEventListener(Class<E> eventClass, IEventListener<E> listener) {
        EventManager.addListener(eventClass, listener, index);
    }

    void setScene(AScene scene, int index) {
        this.scene = scene;
        this.index = index;
        EventManager.addListener(RenderEvent.class, this::onRender, Integer.MAX_VALUE - index);
    }
}
