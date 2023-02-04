package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;
import octi.growth.input.ExitEvent;

public class MainMenuScreen extends AbstractScreen {
    private Stage uiStage;

    public MainMenuScreen(Growth game){
        super(game);
    }

    @Override
    public void show() {
        uiStage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(100).pad(5);

        TextButton playButton = new TextButton("Play", uiSkin);
        //playButton.addListener(new ChangeScreenEvent(game, ScreenType.GAME));
        playButton.addListener(new ChangeScreenEvent(game, ScreenType.GAME_SELECTION_SCREEN));

        TextButton mapEditorButton = new TextButton("Map Editor", uiSkin);
        mapEditorButton.addListener(new ChangeScreenEvent(game, ScreenType.MAP_EDITOR));

        TextButton tutorialButton = new TextButton("Tutorial", uiSkin);
        tutorialButton.addListener(new ChangeScreenEvent(game, ScreenType.TUTORIAL));

        TextButton optionsButton = new TextButton("Options", uiSkin);

        TextButton exitButton = new TextButton("Exit", uiSkin);
        exitButton.addListener(new ExitEvent());

        table.add(playButton).row();
        table.add(mapEditorButton).row();
        table.add(tutorialButton).row();
        table.add(optionsButton).row();
        table.add(exitButton).row();

        uiStage.addActor(table);

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        uiStage.draw();
        uiStage.act(delta);
    }
}
