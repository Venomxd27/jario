package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, xLast, yLast;
    private final boolean[] mouseButtonPressed = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.xLast = 0.0;
        this.yLast = 0.0;
    }

    public static MouseListener get() {
        if(MouseListener.instance == null)
            MouseListener.instance = new MouseListener();
        return MouseListener.instance;
    }

    public static void mousePositionCallback(long window, double xPos, double yPos) {
        get().xLast = get().xPos;
        get().yLast = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        for (int i = 0; i < get().mouseButtonPressed.length; i++) {
            get().isDragging = get().isDragging || get().mouseButtonPressed[i];
        }
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (button >= get().mouseButtonPressed.length) {
            System.err.println("INFO: Unknown mouse button: " + button);
            return;
        }
        if (GLFW_PRESS == action) {
            get().mouseButtonPressed[button] = true;
        }
        else if (action == GLFW_RELEASE) {
            get().mouseButtonPressed[button] = false;
            get().isDragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().xLast = get().xPos;
        get().yLast = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().xLast - get().xPos);
    }

    public static float getDy() {
        return (float) (get().yLast - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

   public static boolean getIsDragging() {
        return get().isDragging;
   }

   public static boolean getMouseButtonPressed(int button) {
        if(button < get().mouseButtonPressed.length)
            return get().mouseButtonPressed[button];
        return false;
   }
}