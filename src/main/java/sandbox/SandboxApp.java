package sandbox;

import com.github.fahjulian.stealth.App;
import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.Entity;
import com.github.fahjulian.stealth.entity.Transform;
import com.github.fahjulian.stealth.event.EventManager;

public class SandboxApp extends App {
    
    public SandboxApp() {
        Log.init("/home/julian/dev/java/Stealth/.log/", true);
        EventManager.addListener(SandboxEventType.SANDBOX_EVENT, this::onSandboxEvent1, 2);
        EventManager.addListener(SandboxEventType.SANDBOX_EVENT, this::onSandboxEvent2, 1);
        EventManager.addListener(SandboxEventType.SANDBOX_EVENT, this::onSandboxEvent3, 0);
        
        Entity e = new Entity("First Entity", new Transform(), new SandboxComponent());
        e.getComponent(SandboxComponentType.SANDBOX).init();
        
        new SandboxEvent();
    }

    public boolean onSandboxEvent1(SandboxEvent e) {
        Log.info("Handling sandbox event in the first method.");
        return false;
    }

    public boolean onSandboxEvent2(SandboxEvent e) {
        Log.info("Handling sandbox event in the second method.");
        return true;
    }

    public boolean onSandboxEvent3(SandboxEvent e) {
        Log.info("Handling sandbox event in the third method.");
        return true;
    }

    public static void main(String... args) {
        new SandboxApp();
    }
}
