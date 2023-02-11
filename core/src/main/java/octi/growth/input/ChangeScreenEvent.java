package octi.growth.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import octi.growth.Growth;
import octi.growth.screen.*;

public class ChangeScreenEvent extends ClickListener {
    private final ScreenType targetScreen;
    private final Growth game;
    private GameplayScreenContext context;

    public ChangeScreenEvent(Growth game, ScreenType targetScreen) {
        this.game = game;
        this.targetScreen = targetScreen;
    }

    public ChangeScreenEvent(Growth game, ScreenType targetScreen, GameplayScreenContext context) {
        this(game, targetScreen);
        this.context = context;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        switch (targetScreen) {
            case GAME:
                game.setScreen(new GameplayScreen(game, context));
                break;
            case TUTORIAL:
                game.setScreen(new TutorialScreen(game));
                break;
            case MAP_EDITOR:
                game.setScreen(new MapEditorScreen(game));
                break;
            case MAIN_MENU:
                game.setScreen(new MainMenuScreen(game));
                break;
            case GAME_SELECTION_SCREEN:
                game.setScreen(new GameSelectionScreen(game));
                break;
            case OPTIONS:
                game.setScreen(new OptionsScreen(game));
                break;
            default:
                throw new IllegalArgumentException("Screen type doesnt exist, please check ScreenTypes.java");
        }
        super.clicked(event, x, y);
    }
}
