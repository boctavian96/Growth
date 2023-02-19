package octi.growth.command;

import com.badlogic.gdx.Screen;
import lombok.AllArgsConstructor;
import octi.growth.Growth;

@AllArgsConstructor
public class RefreshCommand implements Command {
    Growth game;

    @Override
    public void execute() {
        Screen screen = game.getScreen();
        screen.dispose();
        screen.show();
    }
}
