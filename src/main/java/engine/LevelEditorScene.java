package engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
    private String vertexShaderSrc = """
            #version 330 core
            layout (location=0) in vec3 aPos;
            layout (location=1) in vec4 aColor;

            out vec4 fColor;

            void main() {
                fColor =  aColor;
                gl_Position = vec4(aPos , 1.0);
            }""";
    private String fragmentShaderSrc = """
            #version 330 core

            in vec4 fColor;
            out vec4 color;

            void main() {
                color = fColor;
            }""";

    // Compile and link shaders
    // first load and compile the vertex
    private int vertexID, fragmentID, shaderProgram, vaoID, vboID, eboID;

    private float[] vertexArray = {
            //position           //color
            0.5f, -0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f, //Bottom Right
            -0.5f, 0.5f, 0.0f,   0.0f, 1.0f, 0.0f, 1.0f, //Top Left
            0.5f, 0.5f, 0.0f,    0.0f, 0.0f, 1.0f, 1.0f, //Top Right
            -0.5f, -0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f, //Bottom Left
    };

    private int[] elementArray = {
            2, 1, 0, //Top Right Triangle
            0, 1, 3, //Bottom Left Triangle
    };

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Passing the shader source to the GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Checking for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error! Vertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Passing the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Checking for errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error! Fragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // Creating linker
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Checking Linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE){
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Error! Linking failed");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

        // Generating VAO, VBO and EBO buffer and sending to GPU
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Creating indices Buffer
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Adding vertex attributes
        int posSize = 3, colorSize = 4, floatSizeBytes = 4;
        int vertexSizeBytes = (posSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, posSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, posSize * floatSizeBytes);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {
        // Binding shader
        glUseProgram(shaderProgram);

        // Binding VAO
        glBindVertexArray(vaoID);

        // Enabling vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbinding everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        glUseProgram(0);
    }
}
