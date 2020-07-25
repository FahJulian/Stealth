package sandbox;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.ui.AbstractUILayer;
import com.github.fahjulian.stealth.ui.UIButton;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.UIConstraint;
import com.github.fahjulian.stealth.ui.UIConstraints;
import com.github.fahjulian.stealth.ui.UIProperty;

public class SandboxUILayer extends AbstractUILayer<SandboxScene>
{
    private int clickCount = 0;

    public SandboxUILayer(SandboxScene scene)
    {
        super(scene, 3.0f);
    }

    @Override
    protected void onInit()
    {
        UIConstraints c = new UIConstraints(UIConstraint.Type.RELATIVE, 0.1f, 0.25f, 0.15f, 0.25f);
        UIComponent button = new UIButton(this, c, (e) ->
        {
            UIComponent rect = new UIComponent(this,
                    new UIConstraints(UIConstraint.Type.PIXELS, 25, clickCount * 50, 25, 25));
            rect.getProperties().set(UIProperty.Type.HOVER_COLOR, Color.LIGHT_GREY);
            super.add(rect);

            Log.info("Clicks: %d", ++clickCount);
        });

        super.add(button);
    }
}
