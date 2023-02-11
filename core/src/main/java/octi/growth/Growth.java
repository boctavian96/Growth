package octi.growth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import octi.growth.platform.Platform;
import octi.growth.screen.MainMenuScreen;

import java.util.Optional;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Growth extends Game {
    private boolean debugMode = false;
    private Music music;
    private Platform platform;

    public Growth(Platform platform) {
        this.platform = platform;
    }

    @Override
    public void create() {
        Preferences preferences = preparePreferences();

        this.music = Gdx.audio.newMusic(Gdx.files.internal(Constants.BACKGROUND_MUSIC));
        music.play();
        music.setLooping(true);
        music.setVolume(preferences.getFloat("musicVolume"));
        setScreen(new MainMenuScreen(this));
    }

    public void changeDebug() {
        debugMode = !debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    private Preferences preparePreferences() {
        Preferences preferences = Gdx.app.getPreferences("preferences");
        preferences.putFloat("musicVolume", 0.2f);
        preferences.putFloat("soundVolume", 0.2f);
        preferences.putBoolean("muteMusic", false);
        preferences.putBoolean("muteSound", false);
        preferences.flush();

        return preferences;
    }

    public void setMusicVolume(float volume) {
        music.setVolume(volume);
    }

    public void muteSound() {
        Preferences preferences = Gdx.app.getPreferences("preferences");

        boolean isMusicMuted = preferences.getBoolean("muteMusic");

        if (isMusicMuted) {
            music.pause();
        } else {
            music.play();
        }

    }

}
