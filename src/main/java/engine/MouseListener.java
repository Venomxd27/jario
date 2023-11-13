package engine;

import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, xLast, yLast;
    private boolean[] mouseButtonPressed = new boolean[9];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
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
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
//        for (int i = 0; i < get().mouseButtonPressed.length; i++) {
//            get().isDragging = get().isDragging || get().mouseButtonPressed[i];
//        }
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (button >= get().mouseButtonPressed.length) {
            System.err.println("INFO: Unknown mouse button: " + button);
            return;
        }
        if (action == GLFW_PRESS) {
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

    public static float getOrthoX() {
        float currentX = (getX() / (float) Window.getWidth()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        tmp.mul(Window.getScene().camera().getInverseProjection()).mul(Window.getScene().camera().getInverseView());
        currentX = tmp.x;
        System.out.println(currentX);
        return currentX;
    }

    public static float getOrthoY() {
        float currentY = ((Window.getHeight() - getY()) / (float) Window.getHeight()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);
        tmp.mul(Window.getScene().camera().getInverseProjection()).mul(Window.getScene().camera().getInverseView());
        currentY = tmp.y;
        System.out.println(currentY);
        return currentY;
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

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length)
            return get().mouseButtonPressed[button];
        return false;
    }
}
