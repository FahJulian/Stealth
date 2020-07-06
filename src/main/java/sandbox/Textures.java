package sandbox;

import com.github.fahjulian.stealth.graphics.Renderer2D;
import com.github.fahjulian.stealth.graphics.opengl.Texture2D;

public class Textures
{
        public static final Texture2D PLAYER_TEXTURE, //
                        GREEN_TEXTURE, //
                        RED_TEXTURE, //
                        MARIO_TEXTURE;

        static
        {
                Renderer2D.registerTexture(PLAYER_TEXTURE = new Texture2D(
                                "/home/julian/dev/java/Stealth/src/main/resources/textures/player.png"));
                Renderer2D.registerTexture(GREEN_TEXTURE = new Texture2D(
                                "/home/julian/dev/java/Stealth/src/main/resources/textures/green.png"));
                Renderer2D.registerTexture(RED_TEXTURE = new Texture2D(
                                "/home/julian/dev/java/Stealth/src/main/resources/textures/red.png"));
                Renderer2D.registerTexture(MARIO_TEXTURE = new Texture2D(
                                "/home/julian/dev/java/Stealth/src/main/resources/textures/mario.png"));
        }
}
