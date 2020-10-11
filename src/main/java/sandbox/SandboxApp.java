package sandbox;

import com.github.fahjulian.stealth.core.App;

public class SandboxApp extends App {

    private SandboxApp(int width, int height) {
        super("Example App", width, height, ".log/");
        super.run();
    }

    public static void main(String[] args) {
        new SandboxApp(600, 400);
    }
}
