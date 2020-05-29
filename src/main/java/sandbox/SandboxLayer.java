package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.Entity;
import com.github.fahjulian.stealth.entity.IEntityFactory;
import com.github.fahjulian.stealth.entity.Transform;
import com.github.fahjulian.stealth.entity.component.SpriteComponent;
import com.github.fahjulian.stealth.event.mouse.AMouseEvent.Button;
import com.github.fahjulian.stealth.event.mouse.MouseDraggedEvent;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.scene.ALayer;
import com.github.fahjulian.stealth.util.ResourcePool;



public class SandboxLayer extends ALayer {

    private static final String sheetPath = "src/main/resources/textures/mario_small.png";

    static class Factories {
        static final IEntityFactory player = (t) -> 
            new Entity("Player", t, new SpriteComponent(ResourcePool.getSpritesheet(sheetPath).getSprite(0, 0)));
    }


    @Override
    public void onInit() {
        loadResources();
        setShader("src/main/resources/shaders/custom.glsl");

        add(Factories.player.make(new Transform(50.f, 50.f, 152.f, 152.f, 0)));

        addEventListener(MouseDraggedEvent.class, this::onMouseDragged);
    }
    
    public boolean onMouseDragged(MouseDraggedEvent event) {
        if (event.button == Button.RIGHT)
            Log.info(event.toString());
        return false;
    }

    private void loadResources() {
        ResourcePool.addSpritesheet(sheetPath, new Spritesheet(sheetPath, 16, 16, 1));
    }
}