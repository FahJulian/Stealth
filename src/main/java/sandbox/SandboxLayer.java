package sandbox;

import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.WindowCloseEvent;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.graphics.tilemap.TileMap;

@SuppressWarnings("unused")
public class SandboxLayer extends AbstractLayer<SandboxScene>
{
    private TileMap map;

    SandboxLayer(SandboxScene scene)
    {
        super(scene);
    }

    @Override
    protected void onInit()
    {
        scene.test();
        add(Blueprints.player.create(new Transform(0.0f, 0.0f, 0.1f, 160.0f, 160.0f)));

        map = Resources.DEFAULT_MAP;
        // map = createMap(100, 100);

        registerEventListener(RenderEvent.class, this::onRender);
        registerEventListener(WindowCloseEvent.class, this::onWindowClose);
    }

    private void onRender(RenderEvent event)
    {
        Renderer2D.drawTileMap(map);

        for (int i = 0; i < 19000; i++)
        {
            Renderer2D.drawRectangle(10.0f, 10.0f, 2.0f, 100.0f, 100.0f, Color.WHITE);
            Renderer2D.drawRectangle(110.0f, 100.0f, 2.0f, 100.0f, 100.0f, Resources.TILES_SHEET.getSpriteAt(3, 3));
        }
    }

    private void onWindowClose(WindowCloseEvent event)
    {
        map.saveToFile("/home/julian/dev/java/Stealth/.maps/");
    }

    // private TileMap createMap(int width, int height)
    // {
    // Random r = new Random();
    // Sprite[] textureOptions = new Sprite[] {
    // Resources.TILES_SHEET.getSpriteAt(0, 0), //
    // Resources.TILES_SHEET.getSpriteAt(1, 0), //
    // Resources.TILES_SHEET.getSpriteAt(2, 0)
    // };

    // Tile[] tiles = new Tile[width * height];
    // for (int i = 0; i < width * height; i++)
    // tiles[i] = new Tile(textureOptions[r.nextInt(textureOptions.length)]);

    // return new TileMap(new TileMapBlueprint("Generated Map", width, height,
    // 160.0f, 0.0f, tiles));
    // }
}
