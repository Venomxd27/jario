package engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Window {
    private int width, height;
    private String title;
    private static Window window = null;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;

    public float r = 1, b = 1, a = 1, g = 1;
    private static Scene currentScene;
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0 -> currentScene = new LevelEditorScene();
            case 1 -> currentScene = new LevelScene();
            default -> {
                assert false : "Unknown scene '" + newScene + "'";
            }
        }
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run() {
        System.out.println("Shit here we go again");
        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // terminate glfw window
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init() {
        // error call back
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialising GlFW (Graphics library)
        if(!glfwInit())
            throw new IllegalStateException("Bhai glfw chalu nahi hua");

        // configuring glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Creating the window
        // (glfw create returns the address of the window where it is created in memory)
        glfwWindow =  glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL) {
            throw new IllegalStateException("Unable to create glfw window");
        }

        glfwSetCursorPosCallback(glfwWindow , MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(glfwWindow , MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow , MouseListener::mouseScrollCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) ->{
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // keyListener
        glfwSetKeyCallback(glfwWindow , KeyListener::keyCallback);

        // making opengl context current (basically current focus window pe kaam hai)
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync
        glfwSwapInterval(1);  // refresh rate of monitor ke according ke interval pe refresh hoga

        // Now window setup is ready , making window visible
        glfwShowWindow(glfwWindow);

        //bindings available for use
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.imguiLayer = new ImGuiLayer(glfwWindow);
        this.imguiLayer.initImGui();

        Window.changeScene(0);
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)) {
            //poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);  // flush all our colors to screen

            if(dt > 0)
                currentScene.update(dt);

            this.imguiLayer.update(dt);
            glfwSwapBuffers(glfwWindow);
            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public static int getWidth(){
        return get().width;
    }

    public static int getHeight(){
        return get().height;
    }

    public static void setWidth(int newWidth){
        get().width = newWidth;
    }

    public static void setHeight(int newHeight){
        get().height = newHeight;
    }
}
