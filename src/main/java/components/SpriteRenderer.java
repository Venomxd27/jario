package components;

import engine.Component;

public class SpriteRenderer extends Component {
    private boolean firstTime = false;

    @Override
    public void start() {
        System.out.println("INFO: Starting SpriteRenderer");
    }

    @Override
    public void update(float dt) {
        if (!firstTime) {
            System.out.println("INFO: Updating SpriteRenderer");
            firstTime = true;
        }
    }
}
