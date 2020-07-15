package sandbox;

import com.github.fahjulian.stealth.core.resources.ResourcePool;
import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.Spritesheet.SpritesheetBlueprint;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.graphics.tilemap.TileMap;
import com.github.fahjulian.stealth.graphics.tilemap.TileMapBlueprint;

public class Resources
{
    public static final Spritesheet MARIO_SHEET, //
            TILES_SHEET;

    public static final TileMap DEFAULT_MAP;

    static
    {
        SpritesheetBlueprint mario_bp = new SpritesheetBlueprint("mario",
                "/home/julian/dev/java/Stealth/src/main/resources/textures/mario.png", 14, 1, 16, 16, 1);
        MARIO_SHEET = ResourcePool.getOrCreateResource(mario_bp);
        Renderer2D.registerTexture(MARIO_SHEET);

        SpritesheetBlueprint tiles_bp = new SpritesheetBlueprint("tiles",
                "/home/julian/dev/java/Stealth/src/main/resources/textures/tiles.png", 33, 8, 16, 16, 0);
        TILES_SHEET = ResourcePool.getOrCreateResource(tiles_bp);
        Renderer2D.registerTexture(TILES_SHEET);

        TileMapBlueprint defaultMapBP = TileMapBlueprint
                .loadFromFile("/home/julian/dev/java/Stealth/.maps/GeneratedMap.stealthMap.xml");
        DEFAULT_MAP = ResourcePool.getOrCreateResource(defaultMapBP);
    }
}
