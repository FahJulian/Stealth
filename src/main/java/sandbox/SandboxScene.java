package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.event.EventManager;
import com.github.fahjulian.stealth.event.key.KeyPressedEvent;
import com.github.fahjulian.stealth.scene.AScene;

public class SandboxScene extends AScene {

    @Override
    public void onInit() {
        add(new SandboxLayer());
        EventManager.addListener(KeyPressedEvent.class, this::onKeyPressed, 0);
    }

    public boolean onKeyPressed(KeyPressedEvent event) {
        Log.debug(event.toString());
        return true;
    }
}