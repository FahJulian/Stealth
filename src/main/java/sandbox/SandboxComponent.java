package sandbox;

import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;

public class SandboxComponent extends AComponent
{
    @Override
    protected void onInit()
    {
        addEventListener(MouseDraggedEvent.class, this::onMouseDragged);
    }

    private boolean onMouseDragged(MouseDraggedEvent event)
    {
        Log.info("Sandbox Componend registered %s", event);
        return false;
    }
}
