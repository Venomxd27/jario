package jade;


import javax.swing.plaf.synth.SynthTableHeaderUI;
import static org.lwjgl.opengl;

public class LevelEditorScene extends Scene {
    private String vertexShaderSrc ="#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main() {\n" +
            "    fColor =  aColor;\n" +
            "    gl_Position = vec4(aPos , 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main() {\n" +
            "    color = fColor;\n" +
            "}";
    // Compile and link shaders

    //first load and compile the vertex
    private int vertexID, fragmentID, shaderProgram;
    public LevelEditorScene() {

    }
    @Override
    public void init() {
         // Compile and link shaders

        // first load and compile the vertex
        vertexID = gLCreateShader(GL_VERTEX_SHADER);
        //pass the shader source to the gpu
        glShaderSource(vertexID , vertexShaderSrc);
    }
    @Override
    public void update(float dt) {

    }

}
