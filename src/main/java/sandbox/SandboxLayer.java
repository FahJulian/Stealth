package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.event.application.RenderEvent;
import com.github.fahjulian.stealth.event.mouse.MouseButtonPressedEvent;
import com.github.fahjulian.stealth.scene.ASceneLayer;

public class SandboxLayer extends ASceneLayer {

    @Override
    public boolean onRender(RenderEvent event) {
        return false;
    }

    @Override
    public void onInit() {
        addEventListener(MouseButtonPressedEvent.class, this::onMouseButtonPressed);
    }

    public boolean onMouseButtonPressed(MouseButtonPressedEvent event) {
        Log.info("SandboxScene::onMouseButtonPressed: %s", event.toString());
        return true;
    }
    
}