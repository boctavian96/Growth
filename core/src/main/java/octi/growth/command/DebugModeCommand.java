package octi.growth.command;

import lombok.AllArgsConstructor;
import octi.growth.Growth;

@AllArgsConstructor
public class DebugModeCommand implements Command {
    private Growth game;

    @Override
    public void execute() {
        game.changeDebug();
    }
}
