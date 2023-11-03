package engine;

public abstract class Component {
    public GameObject gameObject = null;

    public void start() {
    }

    abstract public void update(float dt);
}
