package sandbox;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.IEntityFactory;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.ALayer;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.mouse.AMouseEvent.Button;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;

public class SandboxLayer extends ALayer
{
    private static final String sheetPath = "src/main/resources/textures/mario_small.png";

    static class Factories
    {
        static final IEntityFactory player = (t) -> new Entity("Player", t);
    }

    @Override
    public void onInit()
    {
        add(Factories.player.create(new Transform(50.f, 50.f, 152.f, 152.f)));

        addEventListener(MouseDraggedEvent.class, this::onMouseDragged);
    }

    public boolean onMouseDragged(MouseDraggedEvent event)
    {
        if (event.button == Button.RIGHT)
            Log.info(event.toString());
        return false;
    }
}
