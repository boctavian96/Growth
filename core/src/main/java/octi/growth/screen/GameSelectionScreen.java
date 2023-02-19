package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import octi.growth.Growth;
import octi.growth.ui.GamePreferencesWidget;

public class GameSelectionScreen extends AbstractScreen {

    private Stage uiStage;

    private GamePreferencesWidget selectionScreenStuff;

    public GameSelectionScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        uiStage = new Stage();

        Table table = new Table();
        table.setFillParent(true);

        selectionScreenStuff = new GamePreferencesWidget(loadSkin(), game);

        table.add(selectionScreenStuff).center();

        uiStage.addActor(table);

        inputMultiplexer.addProcessor(uiStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        uiStage.act(delta);
        uiStage.draw();
    }
}
