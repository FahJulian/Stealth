package sandbox;

import com.github.fahjulian.stealth.AApplication;

public class SandboxApp extends AApplication {

    public SandboxApp(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    protected void onInit() {
        setScene(new SandboxScene());
        getScene().add(new SandboxLayer());
        getScene().init();
    }

    public static void main(String... args) {
        new SandboxApp("Sandbox App", 1000, 1000 / 16 * 9).run();
    }
}


