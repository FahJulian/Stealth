package sandbox;

import static com.github.fahjulian.stealth.graphics.Color.DARK_GREY;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_GREEN;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_GREY;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_MATERIAL;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_MATERIAL;

import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.ui.AbstractUILayer;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.components.UIButton;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;

public class MainMenu extends AbstractUILayer<AbstractScene>
{
    public MainMenu(AbstractScene scene)
    {
        super(scene, 10.0f);
    }

    @Override
    protected void onInit()
    {
        super.registerEventListener(RenderEvent.class, this::onRender);
        super.blockAllInputEventsIf(super::isActive);

        for (int i = 0; i < 5; i++)
        {
            UIComponent b = new UIButton(this, new UIConstraints(100, 50 + i * 75, posZ, 250, 50), i == 4 ? (e) ->
            {
                setActive(false);
            } : (e) ->
            {
            });

            if (i == 4)
            {
                b.getProperties().set(HOVER_MATERIAL, LIGHT_GREEN);
                b.getProperties().set(PRIMARY_MATERIAL, DARK_GREY);
            }

            b.getProperties().set(PRIMARY_MATERIAL, LIGHT_GREY);
            super.add(b);
        }
    }

    private void onRender(RenderEvent event)
    {
        if (active)
            Renderer2D.drawStaticRectangle(0.0f, 0.0f, posZ - 0.001f, Window.get().getWidth(), Window.get().getHeight(),
                    new Color(0.35f, 0.35f, 0.35f, 0.5f));
    }
}
