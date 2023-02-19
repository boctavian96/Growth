package octi.growth.command;

import lombok.AllArgsConstructor;
import octi.growth.Growth;

@AllArgsConstructor
public class MusicCommand implements Command {
    Growth game;
    float musicVolume;

    @Override
    public void execute() {
        game.setMusicVolume(musicVolume);
    }
}
