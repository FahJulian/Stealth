package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.AComponent;
import com.github.fahjulian.stealth.entity.IComponentType;
import com.github.fahjulian.stealth.event.EventManager;

public class SandboxComponent extends AComponent {
     
    @Override
    public void init() {
        EventManager.addListener(SandboxEventType.SANDBOX_EVENT, this::onSandboxEvent, 10);
    }

    @Override
    public IComponentType getType() {
        return SandboxComponentType.SANDBOX;
    }

    @Override
    public String toString() {
        return "SandboxComponent";
    }

    public boolean onSandboxEvent(SandboxEvent event) {
        Log.info("Sandbox component of entity %s handling sandbox event.", getEntity().getName());
        return false;
    }
}
