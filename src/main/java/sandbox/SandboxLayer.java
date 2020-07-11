package sandbox;

import java.util.Random;

import com.github.fahjulian.stealth.core.Window;
import com.github.fahjulian.stealth.core.entity.Transform;
import com.github.fahjulian.stealth.core.scene.AbstractLayer;
import com.github.fahjulian.stealth.events.application.RenderEvent;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;
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

        map = new TileMap("/home/julian/dev/java/Stealth/.maps/SomeMap.stealthMap.xml");
        map.saveToFile("/home/julian/dev/java/Stealth/.maps/");

        registerEventListener(RenderEvent.class, this::onRender);
    }

    private void onRender(RenderEvent event)
    {
        Renderer2D.drawStaticRectangle(0.0f, 0.0f, 2.0f, 100, Window.get().getHeight(), Textures.RED_TEXTURE);

        Renderer2D.drawTileMap(map);
    }

    private Texture2D[] createMap(int width, int height)
    {
        Random r = new Random();
        Texture2D[] textureOptions = new Texture2D[] {
                Textures.PLAYER_TEXTURE, //
                Textures.GREEN_TEXTURE, //
                Textures.RED_TEXTURE, //
        };

        Texture2D[] textures = new Texture2D[width * height];
        for (int i = 0; i < width * height; i++)
            textures[i] = textureOptions[r.nextInt(textureOptions.length)];

        return textures;
    }
}
