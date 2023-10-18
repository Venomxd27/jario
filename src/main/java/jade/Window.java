package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width , height;
    private String title;
    private static Window window = null;
    private long glfwWindow;

    private float r =1,b=1,a =1 , g=1;
    private boolean fadetoblack = false;
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }
    public void run() {
        System.out.println("Shit here we go again");
        init();
        loop();

        //Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //terminate glfw window
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    public void init() {
        // error call back
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialising GlFW (Graphics library)
        if(!glfwInit()) {
            throw new IllegalStateException("Bhai glfw chalu nahi hua");
        }
        // configuring glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE , GLFW_FALSE);   // import vala
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        //creating the window
        //(glfw create returns the address of the window where it is created in memory)
       glfwWindow =  glfwCreateWindow(this.width, this.height,this.title,NULL,NULL);
       if(glfwWindow == NULL) {
           throw new IllegalStateException("Unable to create glfw window");
       }

       glfwSetCursorPosCallback(glfwWindow , MouseListener::mouse_position_call);
       glfwSetMouseButtonCallback(glfwWindow , MouseListener::mousebuttoncallback);
       glfwSetScrollCallback(glfwWindow , MouseListener::mousescroll);

       // keylistener
        glfwSetKeyCallback(glfwWindow , KeyListener::key_callback);

       // making opengl context current (basically current focus window pe kaam hai)
        glfwMakeContextCurrent(glfwWindow);
       // enable v-sync
        glfwSwapInterval(1);  // refresh rate of monitor ke according ke interval pe refresh hoga

        // Now window setup is ready , making window visible
        glfwShowWindow(glfwWindow);

        //bindings available for use
        GL.createCapabilities();
    }
    public void loop() {
        float begintime = Time.get_time();
        float endtime  = Time.get_time();
    while(!glfwWindowShouldClose(glfwWindow)) {
        //poll events
        glfwPollEvents();

        glClearColor(r,g,b,a);
        glClear(GL_COLOR_BUFFER_BIT);  //
        // flush all our colors to screen
        if(fadetoblack) {
            r = Math.max(r - 0.01f,0);
            g = Math.max(g - 0.01f,0);
            b = Math.max(b - 0.01f , 0);
        }
        if(KeyListener.keyPresses(GLFW_KEY_SPACE)) {
            fadetoblack = true;
            System.out.println("yeah ");
        }

        glfwSwapBuffers(glfwWindow);
        endtime = Time.get_time();
        float dt = endtime - begintime;
        begintime = endtime;
    }
    }
}
