package engine;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {
    // Compile and link shaders
    // first load and compile the vertex
    private int vaoID, vboID, eboID;

    private float[] vertexArray = {
        // position                // color
        100.5f, 0.5f, 0.0f,        1.0f, 0.0f, 0.0f, 1.0f, // Bottom Right
        0.5f, 100.5f, 0.0f,        0.0f, 1.0f, 0.0f, 1.0f, // Top Left
        100.5f, 100.5f, 0.0f,      0.0f, 0.0f, 1.0f, 1.0f, // Top Right
        0.5f, 0.5f, 0.0f,          1.0f, 1.0f, 0.0f, 1.0f, // Bottom Left
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
        2, 1, 0, // Top Right Triangle
        0, 1, 3, // Bottom Left Triangle
    };

    private Shader defaultShader;

    public LevelEditorScene() {
    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(0, 0));
        defaultShader = new Shader("assets/shaders/defaults.glsl");
        defaultShader.compile();

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
        camera.cameraPosition.x -= dt * 25.0f;
        camera.cameraPosition.y -= dt * 25.0f;

        defaultShader.use();
        defaultShader.uploadMat4f("uProjMatrix", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uViewMatrix", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());

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

        defaultShader.detach();
    }
}
