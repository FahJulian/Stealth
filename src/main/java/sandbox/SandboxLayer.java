package sandbox;

import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.Renderer2D;
import com.github.fahjulian.stealth.graphics.TileMap2D;
import com.github.fahjulian.stealth.graphics.TileMap2D.TileSet;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class SandboxLayer extends AbstractLayer
{
    SandboxLayer()
    {
    }

    @Override
    protected void onInit()
    {
        add(Blueprints.player.create(new Transform(0.0f, 0.0f, 0.1f, 160.0f, 160.0f)));

        TileMap2D map = new TileMap2D(createMap(), 0.0f);
        registerEventListener(RenderEvent.class, (e) -> Renderer2D.drawTileMap(map));
    }

    @SuppressWarnings("unused")
    private void onRender(RenderEvent event)
    {
        for (int y = 0; y < 316; y++)
        {
            for (int x = 0; x < 316; x++)
            {
                Color color = (x + y) % 2 == 0 ? Color.LIGHT_GREY : Color.DARK_GREY;
                Renderer2D.drawRectangle(x * 100.0f, y * 100.0f, 100.0f, 100.0f, color);
            }
        }
    }

    private TileSet createMap()
    {
        Texture2D[] textures = new Texture2D[] {
                new Texture2D("/home/julian/dev/java/Stealth/src/main/resources/textures/player.png"),
                new Texture2D("/home/julian/dev/java/Stealth/src/main/resources/textures/green.png"),
                new Texture2D("/home/julian/dev/java/Stealth/src/main/resources/textures/red.png"),
                new Texture2D("/home/julian/dev/java/Stealth/src/main/resources/textures/mario_small.png"),
        };
        int[] textureIndices = new int[] {
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, //
                0, 0, 1, 0, 0, 0, 0, 1, 1, 0, //
                0, 0, 1, 0, 0, 2, 0, 0, 0, 0, //
                0, 1, 0, 0, 0, 2, 0, 0, 0, 0, //
                0, 1, 0, 0, 0, 0, 0, 3, 0, 0, //
                0, 0, 0, 1, 0, 0, 0, 3, 0, 0, //
                0, 0, 0, 1, 0, 0, 0, 0, 0, 0, //
                0, 2, 0, 0, 0, 0, 1, 0, 0, 0, //
                1, 0, 3, 0, 3, 0, 2, 0, 0, 0, //
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, //
        };
        TileSet tileSet = new TileSet("Some Map", 160.0f, 10, 10, textures, textureIndices);
        tileSet.saveToFile("/home/julian/dev/java/Stealth/.maps/");
        return tileSet;
    }
}
