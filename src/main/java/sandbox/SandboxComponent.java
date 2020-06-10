package sandbox;

import com.github.fahjulian.stealth.core.entity.AbstractComponent;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;

public class SandboxComponent extends AbstractComponent
{
    @Override
    protected void onInit()
    {
        registerEventListener(MouseDraggedEvent.class, this::onMouseDragged);
    }

    private void onMouseDragged(MouseDraggedEvent event)
    {
        Log.info("Sandbox Componend registered %s", event);
    }
}
