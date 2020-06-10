package sandbox;

import com.github.fahjulian.stealth.core.AbstractApp;
import com.github.fahjulian.stealth.core.scene.AbstractScene;

public class SandboxApp extends AbstractApp
{
    protected SandboxApp(String title, int width, int height)
    {
        super(title, width, height, ".log/", true);
    }

    @Override
    protected AbstractScene onInit()
    {
        return new SandboxScene();
    }

    public static void main(String... args)
    {
        new SandboxApp("Sandbox App", 1000, 1000 * 9 / 16).run();
    }
}
