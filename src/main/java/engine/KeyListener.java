package engine;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private final boolean[] keyPressed = new boolean[350];
    public static KeyListener instance;

    public static KeyListener get() {
        if(KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }
    public static void key_callback(long window, int key, int scancode, int action, int mods)
    {
        if(action == GLFW_PRESS) {
        get().keyPressed[key] = true;
        }
        else if(action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }
    public static boolean keyPresses(int keyCode) {
            return get().keyPressed[keyCode];
    }
}
