package octi.growth;

import com.badlogic.gdx.Game;
import octi.growth.screen.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Growth extends Game {
    private boolean debugMode = false;

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }

    public void changeDebug(){
        debugMode = !debugMode;
    }

    public boolean isDebugMode(){
        return debugMode;
    }
}
