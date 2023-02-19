package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import octi.growth.Constants;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;
import octi.growth.input.ExitEvent;
import octi.growth.input.GlobalKeyboardInput;
import octi.growth.platform.Platform;

public class MainMenuScreen extends AbstractScreen {
    private Stage uiStage;
    private Texture background;

    public MainMenuScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {

        background = new Texture(Gdx.files.internal(Constants.BACKGROUND));
        Image img = new Image(background);

        uiStage = new Stage();
        uiStage.addActor(img);
        Skin uiSkin = loadSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(100).pad(5);

        Label gameName = new Label("Planet Invaders", uiSkin);
        gameName.setFontScale(3);
        gameName.setPosition(280, 420);
        uiStage.addActor(gameName);
        TextButton playButton = new TextButton("Play", uiSkin);
        playButton.addListener(new ChangeScreenEvent(game, ScreenType.GAME_SELECTION_SCREEN));

        TextButton mapEditorButton = new TextButton("Map Editor", uiSkin);
        mapEditorButton.addListener(new ChangeScreenEvent(game, ScreenType.MAP_EDITOR));

        TextButton tutorialButton = new TextButton("Tutorial", uiSkin);
        tutorialButton.addListener(new ChangeScreenEvent(game, ScreenType.TUTORIAL));

        TextButton optionsButton = new TextButton("Options", uiSkin);
        optionsButton.addListener(new ChangeScreenEvent(game, ScreenType.OPTIONS));

        TextButton exitButton = new TextButton("Exit", uiSkin);
        exitButton.addListener(new ExitEvent());

        table.add(playButton).row();
        if (game.getPlatform().equals(Platform.DESKTOP)) {
            table.add(mapEditorButton).row();
        }
        table.add(tutorialButton).row();
        table.add(optionsButton).row();
        if (game.getPlatform().equals(Platform.DESKTOP)) {
            table.add(exitButton).row();
        }

        uiStage.addActor(table);

        inputMultiplexer.addProcessor(uiStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        uiStage.draw();
        uiStage.act(delta);
    }
}
