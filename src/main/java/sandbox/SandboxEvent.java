package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.event.AEvent;
import com.github.fahjulian.stealth.event.IEventType;

public class SandboxEvent extends AEvent {

    public SandboxEvent() {
        Log.info("Raising a new Sandbox event.");
        dispatch();
    }

    @Override
    public IEventType getType() {
        return SandboxEventType.SANDBOX_EVENT;
    }
}
