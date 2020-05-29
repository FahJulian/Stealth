package sandbox;

import com.github.fahjulian.stealth.AApplication;
import com.github.fahjulian.stealth.scene.AScene;

public class SandboxApp extends AApplication {

    protected SandboxApp(String title, int width, int height) {
        super(title, width, height, ".log/", true);
    }

    @Override
    protected AScene onInit() {
        return new SandboxScene();
    }

    public static void main(String... args) {
        new SandboxApp("Sandbox App", 1000, 1000 * 9 / 16).run();
    }
}


