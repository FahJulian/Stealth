package sandbox;

import java.util.Random;

import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;
import com.github.fahjulian.stealth.graphics.Renderer2D;
import com.github.fahjulian.stealth.graphics.TileMap;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

@SuppressWarnings("unused")
public class SandboxLayer extends AbstractLayer<SandboxScene>
{
    private TileMap renderingMap;
    private TileMap map1, map2;

    SandboxLayer(SandboxScene scene)
    {
        super(scene);
    }

    @Override
    protected void onInit()
    {
        scene.test();
        add(Blueprints.player.create(new Transform(0.0f, 0.0f, 0.1f, 160.0f, 160.0f)));

        TileMap map1 = new TileMap("Some Map", 10, 10, 160.0f, 0.0f, createMap(10, 10));
        map1.saveToFile("/home/julian/dev/java/Stealth/.maps/");
        TileMap map2 = new TileMap("/home/julian/dev/java/Stealth/.maps/SomeMap.stealthMap.xml");
        renderingMap = map1;
        registerEventListener(RenderEvent.class, (e) -> Renderer2D.drawTileMap(renderingMap));
        registerEventListener(KeyPressedEvent.class, (e) -> renderingMap = renderingMap == map1 ? map2 : map1);
    }

    private void onRender(RenderEvent event)
    {
        // for (int y = 0; y < 141; y++)
        // {
        // for (int x = 0; x < 141; x++)
        // {
        // Color color = (x + y) % 2 == 0 ? Color.WHITE : Color.DARK_GREY;
        // Renderer2D.drawRectangle(x * 100.0f, y * 100.0f, 100.0f, 100.0f, color);
        // }
        // }

        Renderer2D.drawStaticRectangle(0.0f, 0.0f, 2.0f, Window.get().getWidth(), Window.get().getHeight(),
                Textures.MARIO_TEXTURE);
    }

    private Texture2D[] createMap(int width, int height)
    {
        Random r = new Random();
        Texture2D[] textureOptions = new Texture2D[] {
                Textures.PLAYER_TEXTURE, //
                Textures.GREEN_TEXTURE, //
                Textures.RED_TEXTURE, //
                Textures.MARIO_TEXTURE //
        };

        Texture2D[] textures = new Texture2D[width * height];
        for (int i = 0; i < width * height; i++)
            textures[i] = textureOptions[r.nextInt(4)];

        return textures;
    }
}
