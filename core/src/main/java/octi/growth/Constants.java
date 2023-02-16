package octi.growth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    //Textures.
    public static final String BACKGROUND = "images/background.png";

    //UI.
    public static final String UI_SKIN = "ui/uiskin.json";

    //Sounds.
    public static final String BACKGROUND_MUSIC = "sounds/BGmusic.mp3";
    public static final String LOSE_SOUND = "sounds/planet_conquered.wav";

    //Movement groups.

    //Miscellaneous.
    public static final String EASY_AI = "ai/basicAI.tree";
    public static final String MEDIUM_AI = "ai/normalAI.tree";
    public static final String HARD_AI = "ai/hardAI.tree";

    //Preference Keys.
    public static final String PREFERENCES = "preferences";

    public static final String MUSIC_VOLUME = "musicVolume";
    public static final String SOUND_VOLUME = "soundVolume";
    public static final String MUTE_MUSIC = "muteMusic";
    public static final String MUTE_SOUND = "muteSound";
}
