package engine;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LevelEditorScene extends Scene {


    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-250, 0));

//        int xOffset = 10;
//        int yOffset = 10;
//        float totalWidth = (float)(600 - xOffset * 2);
//        float totalHeight = (float)(300 - yOffset * 2);
//
//        float sizex = totalWidth / 100.0f;
//        float sizey = totalHeight / 100.0f;
//
//        for(int x = 0; x < 100 ; x++) {
//            for(int y = 0; y < 100 ;  y++) {
//                float xPos = xOffset + (x * sizex);
//                float yPos = yOffset + (y * sizey);
//
//                GameObject go = new GameObject("OBJECT" + x + " " + y, new Transform(new Vector2f(xPos,yPos) , new Vector2f(sizex,sizey)));
//                go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth , yPos / totalHeight,1,1)));
//                this.addGameObjectToScene(go);
//            }
//        }

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/mario2.png")));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/mario3.png")));
        this.addGameObjectToScene(obj2);

        loadResources();
    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
    }

    @Override
    public void update(float dt) {


        for (GameObject gameObject : this.gameObjects) {
            gameObject.update(dt);
        }
        this.renderer.render();
    }
}
