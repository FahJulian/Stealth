package sandbox;

import com.github.fahjulian.stealth.core.scene.AScene;

public class SandboxScene extends AScene
{
    @Override
    public void onInit()
    {
        add(new SandboxLayer());
    }
}
