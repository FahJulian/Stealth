package sandbox;

import static com.github.fahjulian.stealth.graphics.Color.DARK_GREEN;
import static com.github.fahjulian.stealth.graphics.Color.DARK_GREY;
import static com.github.fahjulian.stealth.graphics.Color.GREEN;
import static com.github.fahjulian.stealth.ui.constraint.Type.PIXELS;
import static com.github.fahjulian.stealth.ui.constraint.Type.RELATIVE;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER_COLOR;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER_SIZE;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_COLOR;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_COLOR;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.ui.AbstractUILayer;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.components.UIButton;
import com.github.fahjulian.stealth.ui.components.UISlider;
import com.github.fahjulian.stealth.ui.constraint.UIConstraint;
import com.github.fahjulian.stealth.ui.constraint.UIConstraints;

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
        UIConstraints c = new UIConstraints(RELATIVE, 0.1f, 0.1f, 0.1f, 0.07f);
        UIComponent button = new UIButton(this, c, (e) -> Log.info("Clicks: %d", ++clickCount));
        button.getProperties().set(PRIMARY_COLOR, DARK_GREY);
        button.getProperties().set(HOVER_COLOR, DARK_GREEN);
        button.getProperties().set(BORDER_COLOR, GREEN);
        button.getProperties().set(BORDER_SIZE, new UIConstraint(PIXELS, 3));

        UIComponent rect = new UIComponent(this, new UIConstraints(100, 120, 40, 40));

        UIComponent slider = new UISlider(this, new UIConstraints(100, 300, 150, 30), 11, 5,
                (n) -> rect.getProperties().set(PRIMARY_COLOR, new Color(n, n, n, 1.0f)));

        super.add(button, slider, rect);
    }
}
