package renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private String filePath;
    private int texId;

    public Texture(String filePath) {
        this.filePath = filePath;

        // Generate texture on GPU
        texId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texId);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); // S = x-axis
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT); // T = y-axis

        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // When shrinking, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);

        ByteBuffer image = stbi_load(filePath, width, height, channels, 0);

        if (image != null) {
            assert channels.get(0) == 3 || channels.get(0) == 4 : "ERROR: The image file `" + filePath + "` is not RGB or RGBA";
            int channel = channels.get(0) == 4 ? GL_RGBA : GL_RGB;
            glTexImage2D(
                GL_TEXTURE_2D, 0, channel,
                width.get(0), height.get(0), 0,
                channel, GL_UNSIGNED_BYTE, image
            );
        } else {
            assert false : "ERROR: Unable to load image: " + filePath;
        }

        stbi_image_free(image);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texId);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
