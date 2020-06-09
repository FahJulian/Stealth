package sandbox;

import com.github.fahjulian.stealth.components.ColorComponent;
import com.github.fahjulian.stealth.components.RotationComponent;
import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.IEntityFactory;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.ALayer;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.mouse.AMouseEvent.Button;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;

import org.joml.Vector4f;

public class SandboxLayer extends ALayer
{
    static class Factories
    {
        static final IEntityFactory player = (transform) -> new Entity("Player", transform,
                new ColorComponent(new Vector4f(0.2f, 0.15f, 0.8f, 1.0f)),
                new RotationComponent(-180.0f));
    }

    @Override
    public void onInit()
    {
        add(Factories.player.create(new Transform(500.f, 300.f, 152.f, 152.f)));
    }

    public boolean onMouseDragged(MouseDraggedEvent event)
    {
        if (event.button == Button.RIGHT)
            Log.info(event.toString());
        return false;
    }
}
