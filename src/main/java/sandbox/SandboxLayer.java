package sandbox;

import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.IEntityFactory;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.ALayer;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.ResourcePool;
import com.github.fahjulian.stealth.events.mouse.AMouseEvent.Button;
import com.github.fahjulian.stealth.events.mouse.MouseDraggedEvent;
import com.github.fahjulian.stealth.graphics.Spritesheet;



public class SandboxLayer extends ALayer {

    private static final String sheetPath = "src/main/resources/textures/mario_small.png";

    static class Factories {
        static final IEntityFactory player = (t) -> 
            new Entity("Player", t, 
                new SpriteComponent(ResourcePool.getSpritesheet(sheetPath).getSprite(0, 0)),
                new SpriteSwitchingComponent());
    }

    @Override
    public void onInit() {
        loadResources();
        setShader("src/main/resources/shaders/custom.glsl");

        add(Factories.player.create(new Transform(50.f, 50.f, 152.f, 152.f)));

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