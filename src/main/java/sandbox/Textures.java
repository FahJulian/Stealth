package sandbox;

import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.resources.ResourcePool;

public class Textures
{
    public static final Spritesheet MARIO_SHEET, //
            TILES_SHEET;

    static
    {
        Spritesheet.Blueprint mario_bp = new Spritesheet.Blueprint(
                "/home/julian/dev/java/Stealth/src/main/resources/textures/mario.png", 14, 1, 16, 16, 1);
        MARIO_SHEET = ResourcePool.getOrCreateResource(mario_bp);
        Renderer2D.registerTexture(MARIO_SHEET);

        Spritesheet.Blueprint tiles_bp = new Spritesheet.Blueprint(
                "/home/julian/dev/java/Stealth/src/main/resources/textures/tiles.png", 33, 8, 16, 16, 0);
        TILES_SHEET = ResourcePool.getOrCreateResource(tiles_bp);
        Renderer2D.registerTexture(TILES_SHEET);
    }
}
