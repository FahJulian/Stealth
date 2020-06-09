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
import com.github.fahjulian.stealth.graphics.Renderer2D;

import org.joml.Vector4f;

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
        add(Factories.player.create(new Transform(160.f, 160.f, 152.f, 152.f)));

        addEventListener(RenderEvent.class, this::onRender);
    }

    private boolean onRender(RenderEvent event)
    {
        for (int y = 0; y < 20; y++)
        {
            for (int x = 0; x < 20; x++)
            {
                Vector4f color = (x + y) % 2 == 0 ? new Vector4f(0.7f, 0.7f, 0.7f, 1.0f)
                        : new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
                Renderer2D.drawRectangle(x * 100.0f, y * 100.0f, 100.0f, 100.0f, color);
            }
        }
        return false;
    }
}
