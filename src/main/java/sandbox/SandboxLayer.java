package sandbox;

import static com.github.fahjulian.stealth.graphics.Color.WHITE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.fahjulian.stealth.core.entity.Entity;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.resources.SerializablePool;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.application.WindowCloseEvent;
import com.github.fahjulian.stealth.events.key.AbstractKeyEvent.Key;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.graphics.Sprite;
import com.github.fahjulian.stealth.graphics.opengl.AbstractTexture;
import com.github.fahjulian.stealth.graphics.renderer.Renderer2D;
import com.github.fahjulian.stealth.tilemap.TileMap;

@SuppressWarnings("unused")
public class SandboxLayer extends AbstractLayer<SandboxScene>
{
    private static final Random r = new Random();

    private TileMap map;
    private Entity player;

    SandboxLayer(SandboxScene scene)
    {
        super(scene);
    }

    @Override
    protected void onInit()
    {
        Resources.init();

        Transform t = SerializablePool.deserializeFromFile("/home/julian/dev/java/Stealth/.tmp/player_pos.xml");
        player = Blueprints.player.create("Player 1", t);
        super.add(player);

        map = Resources.DEFAULT_MAP;
        super.add(map.getEntities());

        super.registerEventListener(RenderEvent.class, this::onRender);
        super.registerEventListener(WindowCloseEvent.class, this::onWindowClose);
        super.registerEventListener(KeyPressedEvent.class, this::onKeyPressed);
    }

    private void onRender(RenderEvent event)
    {
        Renderer2D.draw(map);

        Renderer2D.drawRectangle(10, 10, 2, 100, 100, WHITE);
        Renderer2D.drawStaticRectangle(10, 10, 2, 100, 100, WHITE);
    }

    private void onKeyPressed(KeyPressedEvent event)
    {
        if (event.getKey() == Key.SPACE)
            map.getTile(1, 1).setSprite(Resources.TILES_SHEET.getSpriteAt(r.nextInt(5), 5));
    }

    private void onWindowClose(WindowCloseEvent event)
    {
        SerializablePool.serializeToFile(map, map.getFilePath() + ".test");
        SerializablePool.serializeToFile(player.getTransform(), "/home/julian/dev/java/Stealth/.tmp/player_pos.xml");
    }

    private TileMap createMap(int width, int height)
    {
        Sprite[] textureOptions = new Sprite[] {
                Resources.TILES_SHEET.getSpriteAt(0, 0), //
                Resources.TILES_SHEET.getSpriteAt(1, 0), //
                Resources.TILES_SHEET.getSpriteAt(2, 0)
        };

        Sprite[] tiles = new Sprite[width * height];
        for (int i = 0; i < width * height; i++)
            tiles[i] = textureOptions[r.nextInt(textureOptions.length)];

        List<AbstractTexture> textures = new ArrayList<>();
        textures.add(Resources.TILES_SHEET);

        TileMap map = new TileMap("/home/julian/dev/java/Stealth/src/main/resources/maps/generated_map.xml", width,
                height, 160.0f, 0.0f, textures, tiles);

        return SerializablePool.getLoaded(map);
    }
}
