package sandbox;

import com.github.fahjulian.stealth.core.scene.AbstractScene;

public class SandboxScene extends AbstractScene
{
    @Override
    protected void onInit()
    {
        add(new SandboxLayer(this));
    }

    void test()
    {

    }
}
