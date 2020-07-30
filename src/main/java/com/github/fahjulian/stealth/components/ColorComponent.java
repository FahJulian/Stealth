package com.github.fahjulian.stealth.components;

import java.util.HashMap;
import java.util.Map;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.entity.AbstractComponentBlueprint;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.util.Toolbox;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

/**
 * Renderes a 2D Rectangle in a given color at the entities position
 */
public class ColorComponent extends AbstractComponent
{
    private final Color color;

    public ColorComponent(Color color)
    {
        this.color = color;
    }

    @Override
    protected void onInit()
    {
        super.registerEventListener(RenderEvent.class, this::onRender);
    }

    private void onRender(RenderEvent event)
    {
        Transform t = entity.getTransform();
        Renderer2D.drawRectangle(t.getPositionX(), t.getPositionY(), t.getPositionZ(), t.getScaleX(), t.getScaleY(),
                color);
    }

    public static final class Blueprint extends AbstractComponentBlueprint<ColorComponent>
    {
        static
        {
            AbstractComponentBlueprint.register(Blueprint.class, Blueprint::deserialize);
        }

        private final Color color;

        public Blueprint(Color color)
        {
            this.color = color;
        }

        public ColorComponent createComponent()
        {
            return new ColorComponent(color);
        }

        @Override
        public String serialize()
        {
            final Map<String, String> fields = new HashMap<>();
            fields.put("color", "some_color");
            return Toolbox.toXml(fields, 8);
        }

        public static Blueprint deserialize(String xml)
        {
            Map<String, String> fields = Toolbox.toFields(xml);
            return new Blueprint(Color.WHITE);
        }
    }
}
