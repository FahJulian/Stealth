package sandbox;

import com.github.fahjulian.stealth.components.KeyboardControlledMovementComponent;
import com.github.fahjulian.stealth.components.RotationComponent;
import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.components.ThirdPersonCameraComponent;
import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.IEntityFactory;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.ALayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.Renderer2D;

public class SandboxLayer extends ALayer
{
    static class Factories
    {
        static final IEntityFactory player = (transform) -> new Entity("Player", transform,
                new SpriteComponent(Renderer2D.PLAYER_TEXTURE), new RotationComponent(0.0f),
                new KeyboardControlledMovementComponent(250.0f), new ThirdPersonCameraComponent());
    }

    @Override
    protected void onInit()
    {
        add(Factories.player.create(new Transform(160.0f, 160.0f, 152.0f, 152.0f)));

        addEventListener(RenderEvent.class, this::onRender);
    }

    private boolean onRender(RenderEvent event)
    {
        for (int y = 0; y < 80; y++)
        {
            for (int x = 0; x < 80; x++)
            {
                Color color = (x + y) % 2 == 0 ? Color.LIGHT_GREY : Color.WHITE;
                Renderer2D.drawRectangle(x * 100.0f, y * 100.0f, 100.0f, 100.0f, color);
            }
        }
        return false;
    }
}
