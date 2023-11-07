package components;

import engine.Component;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;
    public SpriteRenderer(Vector4f colorr) {
        this.color = colorr;
    }

    public SpriteRenderer() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {
    }
    public Vector4f getColor() {
        return this.color;
    }
}
