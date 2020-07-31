package sandbox;

import static com.github.fahjulian.stealth.graphics.Color.WHITE;

import com.github.fahjulian.stealth.components.ColorComponent;
import com.github.fahjulian.stealth.components.FirstPersonCameraComponent;
import com.github.fahjulian.stealth.components.KeyboardControlledMovementComponent;
import com.github.fahjulian.stealth.components.RotationComponent;
import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.core.entity.EntityBlueprint;

public class Blueprints
{
    static final EntityBlueprint player = new EntityBlueprint( //
            new KeyboardControlledMovementComponent.Blueprint(320.0f),
            new SpriteComponent.Blueprint(Resources.MARIO_SHEET.getSpriteAt(0, 0)), //
            new FirstPersonCameraComponent.Blueprint(), //
            new ColorComponent.Blueprint(WHITE), //
            new RotationComponent.Blueprint(3.0f));

    static
    {
        // String xml = player.serialize();
        // try (FileWriter test = new FileWriter(new
        // File("/home/julian/dev/java/Stealth/test.xml")))
        // {
        // test.write(xml);
        // }
        // catch (IOException e)
        // {
        // Log.error("Serializing entity failed.");
        // }

        // EntityBlueprint e = EntityBlueprint.deserialize(xml);
    }
}
