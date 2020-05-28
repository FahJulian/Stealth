package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.component.AComponent;
import com.github.fahjulian.stealth.event.mouse.MouseDraggedEvent;

public class SandboxComponent extends AComponent {

    @Override
    public void onInit() {
        addEventListener(MouseDraggedEvent.class, this::onMouseDragged);
    }

    public boolean onMouseDragged(MouseDraggedEvent event) {
        Log.info("Sandbox Componend registered %s", event);
        return false;
    }

    @Override
    public String toString() {
        return "Sandbox Component";
    }

}
