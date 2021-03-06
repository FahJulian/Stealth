package com.github.fahjulian.stealth.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.events.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.ui.events.UIComponentClickedEvent;
import com.github.fahjulian.stealth.ui.events.UIComponentMouseEnteredEvent;
import com.github.fahjulian.stealth.ui.events.UIComponentMouseExitedEvent;

public abstract class AbstractUILayer<S extends AbstractScene> extends AbstractLayer<S> implements IUIComponent
{
    private final List<UIComponent> components;
    private boolean initialized;

    protected final float posZ;
    protected boolean active;

    public AbstractUILayer(S scene, float posZ)
    {
        super(scene);

        this.components = new ArrayList<>();
        this.posZ = posZ;
        this.initialized = false;
        this.active = true;
    }

    @Override
    public void init(AbstractScene scene)
    {
        super.init(scene);

        super.registerEventListener(MouseMovedEvent.class, this::onMouseMoved);
        super.registerEventListener(MouseButtonPressedEvent.class, this::onMouseButtonPressed);

        for (UIComponent component : components)
            component.init();
        initialized = true;
    }

    public void add(UIComponent c)
    {
        components.add(c);

        if (initialized)
            c.init();
    }

    public void add(UIComponent... components)
    {
        for (UIComponent c : components)
            this.add(c);
    }

    private void onMouseMoved(MouseMovedEvent event)
    {
        float xNow = event.getX(), yNow = event.getY();
        float xBefore = event.getX() - event.getDeltaX(), yBefore = event.getY() - event.getDeltaY();

        for (int i = 0; i < components.size(); i++)
        {
            UIComponent c = components.get(i);
            boolean inNow = (xNow > c.getX() && xNow <= c.getX() + c.getWidth() && yNow > c.getY()
                    && yNow <= c.getY() + c.getHeight());
            boolean outBefore = (xBefore <= c.getX() || xBefore > c.getX() + c.getWidth() || yBefore <= c.getY()
                    || yBefore > c.getY() + c.getHeight());

            if (inNow && outBefore)
                new UIComponentMouseEnteredEvent(c);
            else if (!inNow && !outBefore)
                new UIComponentMouseExitedEvent(c);
        }
    }

    private void onMouseButtonPressed(MouseButtonPressedEvent event)
    {
        float mouseX = event.getX(), mouseY = event.getY();

        for (int i = 0; i < components.size(); i++)
        {
            UIComponent c = components.get(i);
            if (mouseX > c.getX() && mouseX <= c.getX() + c.getWidth() && mouseY > c.getY()
                    && mouseY <= c.getY() + c.getHeight())
                new UIComponentClickedEvent(event.getButton(), c);
        }
    }

    @Override
    public float getWidth()
    {
        return Window.get().getWidth();
    }

    @Override
    public float getHeight()
    {
        return Window.get().getHeight();
    }

    @Override
    public float getX()
    {
        return 0.0f;
    }

    @Override
    public float getY()
    {
        return 0.0f;
    }

    public float getPosZ()
    {
        return posZ;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
