package sandbox;

import com.github.fahjulian.stealth.components.SpriteComponent;
import com.github.fahjulian.stealth.core.resources.ResourcePool;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.Texture;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.tilemap.TileMap;

public class Resources
{
    public static Spritesheet MARIO_SHEET, //
            TILES_SHEET;

    public static Texture TEST_TEXTURE;

    public static TileMap DEFAULT_MAP;

    static void init()
    {
        String xml = SerializablePool.serialize(new Color(0, 0, 0, 0));
        System.out.println(xml);
        Color c = SerializablePool.<Color>deserialize(xml);

        MARIO_SHEET = new Spritesheet("/home/julian/dev/java/Stealth/src/main/resources/textures/mario.png", 14, 1, 16,
                16, 1);
        ResourcePool.getOrLoadResource(MARIO_SHEET);
        Renderer2D.registerTexture(MARIO_SHEET);

        SpriteComponent.Blueprint s = new SpriteComponent.Blueprint(MARIO_SHEET.getSpriteAt(0, 0));
        xml = SerializablePool.serialize(s);
        System.out.println(xml);
        SpriteComponent.Blueprint s1 = SerializablePool.<SpriteComponent.Blueprint>deserialize(xml);
        Log.info("%b", s == s1);

        TILES_SHEET = new Spritesheet("/home/julian/dev/java/Stealth/src/main/resources/textures/tiles.png", 33, 8, 16,
                16, 0);
        ResourcePool.getOrLoadResource(TILES_SHEET);
        Renderer2D.registerTexture(TILES_SHEET);

        TEST_TEXTURE = ResourcePool.getOrLoadResource(
                new Texture("/home/julian/dev/java/StealthMapEditor/src/main/resources/textures/default.png"));

        ResourcePool.getOrLoadResource(DEFAULT_MAP = ResourcePool.getOrLoadResource(
                TileMap.fromFile("/home/julian/dev/java/Stealth/src/main/resources/maps/default.xml")));
    }
}
