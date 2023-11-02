package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    public static MouseListener current;
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
        if(MouseListener.current == null) {
            MouseListener.current = new MouseListener();
        }
        return MouseListener.current;
    }

    // mouse positioning
    public static void mouse_position_call(long window, double xPos, double yPos) {
        get().xLast = get().xPos;
        get().yLast = get().yPos;
        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
        // drag kar rahe to kaise pata chalega , ek to mouse clicked hai aur move kar rahe hai to usko drag kar keh sakte hai na
    }

    // mouse button operations
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (GLFW_PRESS == action) {
            get().mouseButtonPressed[button] = true;
        }
        else if (action == GLFW_RELEASE) {
            get().mouseButtonPressed[button] = false;
            get().isDragging = false;
        }
    }

    public static void mouseScroll(long window, double xScroll, double yScroll) {
        get().scrollX = xScroll;
        get().scrollY = yScroll;
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

   public static boolean mouseButtonPressed(int button) {
        if(button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        }
        else {
            return false;
        }
   }

}
