package sandbox;

import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.ui.UIButton;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.UIConstraint.Type;
import com.github.fahjulian.stealth.ui.UIConstraints;
import com.github.fahjulian.stealth.ui.UILayer;

public class SandboxScene extends AbstractScene
{
    private int clickCount = 0;

    @Override
    protected void onInit()
    {
        add(new SandboxLayer(this));

        UILayer ui = new UILayer(this, 3.0f);
        UIConstraints c = new UIConstraints(Type.RELATIVE, 0.25f, 0.25f, 0.25f, 0.25f);
        UIComponent button = new UIButton(ui, c, (e) ->
        {
            Log.info("Clicks: %d", ++clickCount);
        });

        ui.add(button);
        add(ui);
    }

    void test()
    {

    }
}
