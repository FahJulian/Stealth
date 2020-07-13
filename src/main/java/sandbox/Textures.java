package sandbox;

import com.github.fahjulian.stealth.graphics.Spritesheet;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;

public class Textures
{
    public static final Spritesheet MARIO_SHEET, //
            TILES_SHEET;

    static
    {
        Renderer2D.registerTexture(MARIO_SHEET = new Spritesheet(
                "/home/julian/dev/java/Stealth/src/main/resources/textures/mario.png", 14, 1, 16, 16, 1));
        Renderer2D.registerTexture(TILES_SHEET = new Spritesheet(
                "/home/julian/dev/java/Stealth/src/main/resources/textures/tiles.png", 33, 8, 16, 16, 0));
    }
}
