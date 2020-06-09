package sandbox;

import com.github.fahjulian.stealth.core.scene.AScene;

public class SandboxScene extends AScene
{
    @Override
    protected void onInit()
    {
        add(new SandboxLayer());
    }
}
