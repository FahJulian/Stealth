package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.Entity;
import com.github.fahjulian.stealth.entity.Transform;
import com.github.fahjulian.stealth.event.application.RenderEvent;
import com.github.fahjulian.stealth.event.mouse.MouseMovedEvent;
import com.github.fahjulian.stealth.scene.ALayer;

public class SandboxLayer extends ALayer {

    @Override
    public boolean onRender(RenderEvent event) {
        return false;
    }

    @Override
    public void onInit() {
        addEventListener(MouseMovedEvent.class, this::onMouseMoved);

        Entity e = new Entity("Sandbox Entity", new Transform(), new SandboxComponent());
        add(e);
    }
    
    public boolean onMouseMoved(MouseMovedEvent event) {
        Log.info(event.toString());
        return false;
    }
}