package renderer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;
    private String vertexSource;
    private String fragmentSource;
    private final String filePath;

    public Shader(String filePath) {
        this.filePath = filePath;
        try {
            String source = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] sources = source.split("(#type)( )+([a-zA-Z]+)");

            System.out.println("INFO: sources: " + Arrays.toString(sources));
            System.out.println("INFO: source" + source);

            int index = source.indexOf("#type") + 5;
            int eol = source.indexOf("\n", index);
            System.out.println("INFO: index: " + index + "\neol: " + eol);
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 5;
            eol = source.indexOf("\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                assert secondPattern.equals("fragment") : "ERROR: First shader is `vertex` type but second shader is not `fragment` type";
                vertexSource = sources[1];
                fragmentSource = sources[2];
            } else if (firstPattern.equals("fragment")) {
                assert secondPattern.equals("vertex") : "ERROR: First shader is `fragment` type but second shader is not `vertex` type";
                vertexSource = sources[2];
                fragmentSource = sources[1];
            } else {
                assert false : "ERROR: Shader type must be 'vertex' or 'fragment'";
            }
        } catch (IOException e) {
            e.printStackTrace();
            assert false : "ERROR: Unable to open shader file: `" + filePath + "`";
        }

        System.out.println("INFO: vertexSource: " + vertexSource);
        System.out.println("INFO: fragmentSource: " + fragmentSource);
    }

    public void compile() {
        int vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Passing the shader source to the GPU
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        // Checking for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: Vertex shader compilation failed for file: " + filePath);
            System.err.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Passing the shader source to the GPU
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // Checking for errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: Fragment shader compilation failed for file: " + filePath);
            System.err.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // Creating linker
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Checking Linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE){
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Linking failed");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }

    public void use() {
        // Binding shader
        glUseProgram(shaderProgramID);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }
}
