package jade;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width , height;
    private String title;
    private static Window window = null;
    private long glfwWindow;
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
    while(!glfwWindowShouldClose(glfwWindow)) {
        //poll events
        glfwPollEvents();

        glClearColor(1.0f,0.0f,0.0f,1.0f);
        glClear(GL_COLOR_BUFFER_BIT);  // flush all our colors to screen

        glfwSwapBuffers(glfwWindow);

    }
    }
}
