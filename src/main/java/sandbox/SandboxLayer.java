package sandbox;

import java.util.Random;

import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.WindowCloseEvent;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.graphics.tilemap.Tile;
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
        Resources.init();

        scene.test();
        add(Blueprints.player.create(new Transform(0.0f, 0.0f, 0.1f, 160.0f, 160.0f)));

        map = Resources.DEFAULT_MAP;
        // map = createMap(10, 10);

        registerEventListener(RenderEvent.class, this::onRender);
        registerEventListener(WindowCloseEvent.class, this::onWindowClose);
    }

    private void onRender(RenderEvent event)
    {
        Renderer2D.draw(map);
    }

    private void onWindowClose(WindowCloseEvent event)
    {
        map.save();
    }

    private TileMap createMap(int width, int height)
    {
        Random r = new Random();
        Sprite[] textureOptions = new Sprite[] {
                Resources.TILES_SHEET.getSpriteAt(0, 0), //
                Resources.TILES_SHEET.getSpriteAt(1, 0), //
                Resources.TILES_SHEET.getSpriteAt(2, 0)
        };

        Tile[] tiles = new Tile[width * height];
        for (int i = 0; i < width * height; i++)
            tiles[i] = new Tile(textureOptions[r.nextInt(textureOptions.length)]);

        TileMap map = TileMap.create("/home/julian/dev/java/Stealth/.maps/GeneratedMap.stealthMap.xml", width, height,
                160.0f, 0.0f, tiles);

        try
        {
            map.load();
        }
        catch (Exception e)
        {
        }

        return map;
    }
}
