package sandbox;

import com.github.fahjulian.stealth.AApplication;
import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.event.EventManager;
import com.github.fahjulian.stealth.event.application.UpdateEvent;

public class SandboxApp extends AApplication {

    protected static String logDir = ".loga/";
    protected static boolean debug = false;

    public SandboxApp(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    protected void init() {
        EventManager.addListener(UpdateEvent.class, this::onUpdate, 0);
    }

    public boolean onUpdate(UpdateEvent event) {
        Log.info(event.toString());
        return false;
    }

    public static void main(String... args) {
        new SandboxApp("Sandbox App", 500, 500).run();
    }
}


