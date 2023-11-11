package components;

import engine.Component;
import engine.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class SpriteRenderer extends Component {

    private Vector4f color;
    public Sprite sprite;

    private Transform lastTransform;
    private boolean isFlag = false;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1,1,1,1);
    }

    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)){
            this.gameObject.transform.copy(this.lastTransform);
            isFlag = true;
        }
    }
    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture(){
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords(){
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
        this.isFlag = true;
    }

    public void setColor(Vector4f color){
        if (!this.color.equals(color)){
            this.color.set(color);
            this.isFlag = true;
        }
    }

    public boolean isFlag(){
        return this.isFlag;
    }

    public void setClean(){
        this.isFlag = false;
    }
}
