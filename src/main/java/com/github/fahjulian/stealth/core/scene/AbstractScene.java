package com.github.fahjulian.stealth.core.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.event.AbstractEvent;
import com.github.fahjulian.stealth.core.event.EventDispatcher;
import com.github.fahjulian.stealth.core.event.IEventLayer;
import com.github.fahjulian.stealth.core.event.IEventLayerStack;
import com.github.fahjulian.stealth.core.event.IEventListener;

/**
 * A Scene holds (is) a Layerstack and an EventDispatcher that gets notified by
 * events if the Scene is the current Scene of the App.
 */
public abstract class AbstractScene implements IEventLayerStack
{
    private final List<AbstractLayer> layers;
    private final EventDispatcher eventDispatcher;
    private final Camera camera;
    private boolean initialized;

    protected <S extends AbstractScene> AbstractScene()
    {
        this.eventDispatcher = new EventDispatcher(this);
        layers = new ArrayList<>();
        camera = new Camera(0, 0);
        initialized = false;
    }

    /**
     * This method gets called during initialization of the App. It should add all
     * Layers to the scene.
     */
    abstract protected void onInit();

    /**
     * Initializes the scene.
     */
    public void init()
    {
        onInit();

        for (AbstractLayer layer : layers)
            layer.init(this);

        initialized = true;
    }

    /**
     * Add a Layer to the Scene and initializes it if the scene is already
     * initialized.
     * 
     * @param layer
     *                  The Layer to add
     */
    public void add(AbstractLayer layer)
    {
        layers.add(layer);
        if (initialized)
            layer.init(this);
    }

    protected <E extends AbstractEvent> void registerEventListener(Class<E> eventClass, IEventListener<E> listener)
    {
        eventDispatcher.registerEventListener(eventClass, listener);
    }

    @Override
    public int compare(IEventLayer o1, IEventLayer o2)
    {
        return Integer.compare(layers.indexOf(o2), layers.indexOf(o1));
    }

    public Camera getCamera()
    {
        return camera;
    }

    public EventDispatcher getEventDispatcher()
    {
        return eventDispatcher;
    }
}
