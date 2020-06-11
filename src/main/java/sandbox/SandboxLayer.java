package sandbox;

import com.github.fahjulian.stealth.components.KeyboardControlledMovementComponent;
import com.github.fahjulian.stealth.components.RotationComponent;
import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.components.ThirdPersonCameraComponent;
import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.IEntityFactory;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.Renderer2D;

import org.joml.Vector3f;

public class SandboxLayer extends AbstractLayer
{

    static class Factories
    {
        static final IEntityFactory player = (transform) -> new Entity("Player", transform,
                new SpriteComponent(Renderer2D.PLAYER_TEXTURE), //
                new RotationComponent(0.0f), //
                new KeyboardControlledMovementComponent(250.0f), //
                new ThirdPersonCameraComponent());
    }

    @Override
    protected void onInit()
    {
        add(Factories.player
                .create(new Transform(new Vector3f(100.0f, 100.0f, 2.0f), new Vector3f(160.0f, 160.0f, 0.0f))));

        registerEventListener(RenderEvent.class, this::onRender);
    }

    private void onRender(RenderEvent event)
    {
        for (int y = 0; y < 316; y++)
        {
            for (int x = 0; x < 316; x++)
            {
                Color color = (x + y) % 2 == 0 ? Color.LIGHT_GREY : Color.DARK_GREY;
                Renderer2D.drawRectangle(x * 100.0f, y * 100.0f, 100.0f, 100.0f, color);
            }
        }
    }
}
