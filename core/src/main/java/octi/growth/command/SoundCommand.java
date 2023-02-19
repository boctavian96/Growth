package octi.growth.command;

import lombok.AllArgsConstructor;
import octi.growth.Growth;

@AllArgsConstructor
public class SoundCommand implements Command {
    Growth game;
    float soundVolume;

    @Override
    public void execute() {
        //TODO: Implement me!
        throw new UnsupportedOperationException("Implement me!");
    }
}
