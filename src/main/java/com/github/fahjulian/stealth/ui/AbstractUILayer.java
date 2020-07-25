package com.github.fahjulian.stealth.ui;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.events.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

public abstract class AbstractUILayer<S extends AbstractScene> extends AbstractLayer<S> implements IUIParent
{
    private final List<UIComponent> components;
    private final float posZ;
    private boolean initialized;

    public AbstractUILayer(S scene, float posZ)
    {
        super(scene);

        this.components = new ArrayList<>();
        this.posZ = posZ;
        this.initialized = false;
    }

    @Override
    public void init(AbstractScene scene)
    {
        super.init(scene);

        super.registerEventListener(RenderEvent.class, this::onRender);
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

    private void onRender(RenderEvent event)
    {
        for (int i = 0; i < components.size(); i++)
        {
            UIComponent c = components.get(i);
            Renderer2D.drawStaticRectangle( //
                    c.getX(), //
                    c.getY(), //
                    posZ, //
                    c.getWidth(), //
                    c.getHeight(), //
                    c.getColor());
        }
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
                new MouseEnteredEvent(c);
            else if (!inNow && !outBefore)
                new MouseExitedEvent(c);
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
}
