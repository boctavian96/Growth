package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import octi.growth.Growth;
import octi.growth.model.GameMap;
import octi.growth.ui.TutorialWidget;

public class TutorialScreen extends AbstractScreen {

    private Stage tutorialStage;
    private GameMap tutorialMap;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    public TutorialScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        tutorialStage = new Stage();
        Table t = new Table();
        t.setFillParent(true);
        t.add(new TutorialWidget(game, loadSkin()));

        tutorialStage.addActor(t);

        tutorialMap = new GameMap(game, GameplayScreenContext.tutorialContext(), tutorialStage);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        inputMultiplexer.addProcessor(tutorialStage);
        inputMultiplexer.addProcessor(tutorialMap);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        tutorialMap.draw(shapeRenderer, batch, delta);

        tutorialStage.act(delta);
        tutorialStage.draw();

        tutorialMap.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.F5)) {
            refresh();
        }
    }

    public void refresh() {
        Gdx.app.log("REFRESH", "Refreshing the screen");
        dispose();
        show();
    }

    @Override
    public void dispose() {
        tutorialStage.dispose();
        super.dispose();
    }
}
