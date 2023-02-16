package octi.growth;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import octi.growth.platform.Platform;
import octi.growth.screen.MainMenuScreen;

import java.util.Optional;

import static octi.growth.Constants.*;

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

        this.music = Gdx.audio.newMusic(Gdx.files.internal(BACKGROUND_MUSIC));
        music.play();
        music.setLooping(true);
        music.setVolume(preferences.getFloat(MUSIC_VOLUME));
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
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES);
        preferences.putFloat(MUSIC_VOLUME, 0.2f);
        preferences.putFloat(SOUND_VOLUME, 0.2f);
        preferences.putBoolean(MUTE_MUSIC, false);
        preferences.putBoolean(MUTE_SOUND, false);
        preferences.flush();

        return preferences;
    }

    public void setMusicVolume(float volume) {
        music.setVolume(volume);
    }

    public void muteSound() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES);

        boolean isMusicMuted = preferences.getBoolean(MUTE_MUSIC);

        if (isMusicMuted) {
            music.pause();
        } else {
            music.play();
        }

    }

}
