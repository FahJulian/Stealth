package sandbox;

import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.core.entity.AComponent;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.core.util.ResourcePool;
import com.github.fahjulian.stealth.events.key.AKeyEvent.Key;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.graphics.Spritesheet;

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
}
