package sandbox;

import com.github.fahjulian.stealth.components.KeyboardControlledMovementComponent;
import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.components.ThirdPersonCameraComponent;
import com.github.fahjulian.stealth.core.entity.EntityBlueprint;

public class Blueprints
{
    static final EntityBlueprint player = new EntityBlueprint( //
            new KeyboardControlledMovementComponent.Blueprint(320.0f),
            new SpriteComponent.Blueprint(Resources.MARIO_SHEET.getSpriteAt(0, 0)), //
            new ThirdPersonCameraComponent.Blueprint());
}
