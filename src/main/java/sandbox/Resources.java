package sandbox;

import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.tilemap.TileMap;

public class Resources
{
    public static Spritesheet MARIO_SHEET, //
            TILES_SHEET;

    public static TileMap DEFAULT_MAP;

    static void init()
    {
        Renderer2D.registerTexture(MARIO_SHEET = SerializablePool.getLoaded(new Spritesheet(
                "/home/julian/dev/java/Stealth/src/main/resources/textures/mario.png", 14, 1, 16, 16, 1)));

        Renderer2D.registerTexture(TILES_SHEET = SerializablePool.getLoaded(new Spritesheet(
                "/home/julian/dev/java/Stealth/src/main/resources/textures/tiles.png", 33, 8, 16, 16, 0)));

        DEFAULT_MAP = SerializablePool.<TileMap>deserializeFromFile("/home/julian/dev/java/Stealth/test.xml");
    }
}
