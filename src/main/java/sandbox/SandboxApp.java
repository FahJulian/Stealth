package sandbox;

import com.github.fahjulian.stealth.AApplication;

public class SandboxApp extends AApplication {

    public SandboxApp(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    protected void onInit() {
        SandboxScene scene = new SandboxScene();
        window.setScene(scene);
    }

    public static void main(String... args) {
        new SandboxApp("Sandbox App", 500, 500).run();
    }
}


