package jade;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {
    public LevelEditorScene() {
        System.out.println("Initiated level editor scene");
    }
    private static boolean changingscene = false;
    private static float timetochangescene = 2.0f;
    @Override
    public void update(float dt) {
        if(!changingscene && KeyListener.keyPresses(KeyEvent.VK_SPACE)) {
            changingscene = true;
        }
        if(changingscene && timetochangescene > 0) {
            timetochangescene -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt* 5.0f;
            Window.get().b -= dt*5.0f;
        }
        else if(changingscene) {
        Window.changescene(1);
        }
    }

}
