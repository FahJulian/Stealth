package sandbox;

import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.component.AComponent;
import com.github.fahjulian.stealth.entity.component.SpriteComponent;
import com.github.fahjulian.stealth.event.key.AKeyEvent.Key;
import com.github.fahjulian.stealth.event.key.KeyPressedEvent;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.util.ResourcePool;

public class SpriteSwitchingComponent extends AComponent {

    private int i = 0;

    @Override
    public void onInit() {
        if (!entity.hasComponent(SpriteComponent.class)) {
            Log.warn("Cant init SpriteSwitchingComponent on Entity %s without SpriteComponent", entity);
            return;       
        }

        addEventListener(KeyPressedEvent.class, this::onKeyPressed);
    }

    private boolean onKeyPressed(KeyPressedEvent event) {
        if (event.getKey() == Key.SPACE) {
            if (i == 14) i = 0;
            Spritesheet sheet = ResourcePool.getSpritesheet("src/main/resources/textures/mario_small.png");
            entity.getComponent(SpriteComponent.class).setSprite(sheet.getSprite(i++, 0));
        }

        return false;
    }

    @Override
    public String toString() {
        return "Player Component";
    }
}
