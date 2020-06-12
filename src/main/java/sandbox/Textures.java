package sandbox;

import com.github.fahjulian.stealth.graphics.Renderer2D;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Textures
{
    public static final Texture2D PLAYER_TEXTURE;

    static
    {
        Renderer2D.registerTexture(PLAYER_TEXTURE = new Texture2D("src/main/resources/textures/player.png"));
    }
}
