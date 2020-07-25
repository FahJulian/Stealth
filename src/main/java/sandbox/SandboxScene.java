package sandbox;

import com.github.fahjulian.stealth.core.scene.AbstractScene;

public class SandboxScene extends AbstractScene
{
    @Override
    protected void onInit()
    {
        super.add(new SandboxLayer(this));
        super.add(new SandboxUILayer(this));
    }
}
