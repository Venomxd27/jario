package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    public static MouseListener current;
    private double scroll_x , scroll_y;
    private double x_pos,y_pos,x_last,y_last;
    private boolean mouse_button_pressed[] = new boolean[3];
    private boolean drag_kar_raha;

    private MouseListener() {
        this.x_pos = 0.0;
        this.y_pos = 0.0;
        this.x_last = 0.0;
        this.y_last = 0.0;

    }
    public static MouseListener get() {
        if(MouseListener.current == null) {
            MouseListener.current = new MouseListener();
        }
        return MouseListener.current;
    }

    // mouse positioning
    public static void mouse_position_call(long window , double X_pos,double Y_pos) {
        get().x_last = get().x_pos;
        get().y_last = get().y_pos;
        get().x_pos = X_pos;
        get().y_pos = Y_pos;
        get().drag_kar_raha = get().mouse_button_pressed[0] || get().mouse_button_pressed[1] || get().mouse_button_pressed[2];
        // drag kar rahe to kaise pata chalega , ek to mouse clicked hai aur move kar rahe hai to usko drag kar keh sakte hai na
    }

    // mouse button operations
    public static void mousebuttoncallback(long window , int button , int action , int mods) {
        if(GLFW_PRESS == action) {
            get().mouse_button_pressed[button] = true;
        }
        else if(action == GLFW_RELEASE) {
            get().mouse_button_pressed[button] = false;
            get().drag_kar_raha = false;
        }
    }

    public static void mousescroll(long window , double X_scroll , double Y_scroll ) {
        get().scroll_x = X_scroll;
        get().scroll_y = Y_scroll;
    }
    public static void endframe() {
        get().scroll_x = 0;
        get().scroll_y = 0;
        get().x_last  = get().x_pos;
        get().y_last = get().y_pos;

    }

    public static float getx() {
        return (float)get().x_pos;
    }
    public static float gety() {
        return (float)get().y_pos;
    }
    public static float get_Dx() {
        return (float)(get().x_last - get().x_pos);
    }
    public static float get_Dy() {
        return (float)(get().y_last - get().y_pos);
    }
    public static float getscrollx() {
        return (float)get().scroll_x;
    }
    public static float getscroll_y() {
        return (float)get().scroll_y;
    }
   public static boolean  isdragging() {
        return (boolean)get().drag_kar_raha;
   }
   public static boolean mouse_button_pressed(int button) {
        if(button < get().mouse_button_pressed.length)
        {
            return get().mouse_button_pressed[button];
        }
        else {
            return false;
        }
   }

}
