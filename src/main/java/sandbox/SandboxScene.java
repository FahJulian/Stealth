package sandbox;

import static com.github.fahjulian.stealth.events.key.AbstractKeyEvent.Key.ESCAPE;

import com.github.fahjulian.stealth.core.scene.AbstractScene;
import com.github.fahjulian.stealth.events.key.KeyPressedEvent;

public class SandboxScene extends AbstractScene
{
    private MainMenu mainMenu;

    @Override
    protected void onInit()
    {
        super.add(new SandboxLayer(this));
        super.add(new SandboxUILayer(this));
        super.add(mainMenu = new MainMenu(this));

        super.registerEventListener(KeyPressedEvent.class, (e) ->
        {
            if (e.getKey() == ESCAPE)
                mainMenu.setActive(!mainMenu.isActive());
        });
    }
}
