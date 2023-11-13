package components;

public class FontRenderer extends Component {

    @Override
    public void start() {
        if (gameObject.getComponent(SpriteRenderer.class
        ) != null) {
            System.out.println("INFO: Starting FontRenderer");
        }
    }

    @Override
    public void update(float dt) {
        System.out.println("INFO: Updating FontRenderer");
    }
}
