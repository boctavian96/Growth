package octi.growth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import octi.growth.screen.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Growth extends Game {
    private boolean debugMode = false;
    private Sound sound ;
    private long id;

    @Override
    public void create() {
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/BGmusic.mp3"));
        this.id = sound.play(1.0f); // play new sound and keep handle for further manipulation
        sound.setLooping( id, true);
        setScreen(new MainMenuScreen(this));
    }

    public void changeDebug(){
        debugMode = !debugMode;
    }

    public boolean isDebugMode(){
        return debugMode;
    }
}
