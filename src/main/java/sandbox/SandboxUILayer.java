package sandbox;

import static com.github.fahjulian.stealth.graphics.Color.BLACK;
import static com.github.fahjulian.stealth.graphics.Color.DARK_GREEN;
import static com.github.fahjulian.stealth.graphics.Color.DARK_GREY;
import static com.github.fahjulian.stealth.graphics.Color.GREEN;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_BLUE;
import static com.github.fahjulian.stealth.graphics.Color.LIGHT_GREY;
import static com.github.fahjulian.stealth.ui.constraint.Type.RELATIVE;
import static com.github.fahjulian.stealth.ui.property.Types.BORDER;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_BORDER;
import static com.github.fahjulian.stealth.ui.property.Types.HOVER_MATERIAL;
import static com.github.fahjulian.stealth.ui.property.Types.PRIMARY_MATERIAL;

import com.github.fahjulian.stealth.core.util.Log;
import com.github.fahjulian.stealth.graphics.Color;
import com.github.fahjulian.stealth.ui.AbstractUILayer;
import com.github.fahjulian.stealth.ui.UIBorder;
import com.github.fahjulian.stealth.ui.UIComponent;
import com.github.fahjulian.stealth.ui.components.UIButton;
import com.github.fahjulian.stealth.ui.components.UICheckbox;
import com.github.fahjulian.stealth.ui.components.UISlider;
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
        UIConstraints c = new UIConstraints(RELATIVE, 0.1f, 0.1f, posZ, 0.1f, 0.07f);
        UIComponent button = new UIButton(this, c, (e) -> Log.info("Clicks: %d", ++clickCount));
        button.getProperties().set(PRIMARY_MATERIAL, Resources.MARIO_SHEET.getSpriteAt(0, 0));
        button.getProperties().set(HOVER_MATERIAL, DARK_GREEN);
        button.getProperties().set(BORDER, new UIBorder(GREEN, 5));
        button.getProperties().set(HOVER_BORDER, new UIBorder(LIGHT_BLUE, 10));

        UIComponent rect = new UIComponent(this, new UIConstraints(100, 120, posZ, 40, 40));

        UIComponent slider = new UISlider(this, new UIConstraints(100, 300, posZ, 150, 30), 11, 5,
                (n) -> rect.getProperties().set(PRIMARY_MATERIAL, new Color(n, n, n, 1.0f)));

        UIComponent checkbox = new UICheckbox(this, new UIConstraints(100, 200, posZ, 25, 25), 2, 2, BLACK, LIGHT_GREY,
                DARK_GREY, (b) -> Log.info("Checkbox %s", b ? "ckecked" : "unchecked"));

        super.add(button, slider, rect, checkbox);
    }
}
