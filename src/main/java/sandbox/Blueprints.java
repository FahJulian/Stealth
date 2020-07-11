package sandbox;

import com.github.fahjulian.stealth.components.ColorComponent;
import com.github.fahjulian.stealth.components.KeyboardControlledMovementComponent;
import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.components.ThirdPersonCameraComponent;
import com.github.fahjulian.stealth.core.entity.EntityBlueprint;
import com.github.fahjulian.stealth.graphics.Color;

public class Blueprints
{
        static final EntityBlueprint lightMapTile = new EntityBlueprint("Map Tile",
                        new ColorComponent.Blueprint(Color.LIGHT_GREY));
        static final EntityBlueprint darkMapTile = new EntityBlueprint("Map Tile",
                        new ColorComponent.Blueprint(Color.DARK_GREY));

        static final EntityBlueprint player = new EntityBlueprint("Player",
                        new KeyboardControlledMovementComponent.Blueprint(320.0f),
                        new SpriteComponent.Blueprint(Textures.PLAYER_TEXTURE), //
                        new ThirdPersonCameraComponent.Blueprint());
}
