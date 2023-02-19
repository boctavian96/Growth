package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import octi.growth.Growth;
import octi.growth.input.GlobalKeyboardInput;
import octi.growth.model.GameMap;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameplayScreen extends AbstractScreen {
    private final GameplayScreenContext context;

    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private GameMap map;

    private final Stage uiStage;

    private final InputMultiplexer inputMultiplexer;

    public GameplayScreen(Growth game, GameplayScreenContext context) {
        super(game);
        this.context = context;
        this.uiStage = new Stage();

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(uiStage);
    }

    @Override
    public void show() {
        // Prepare your screen here.

        // Prepare Camera.
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(w / 2, h / 2, 0);
        camera.update();
        FitViewport fitViewport = new FitViewport(w, h);
        inputMultiplexer.addProcessor(new GlobalKeyboardInput(game));

        // Prepare Drawers.
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        //Prepare Map.
        map = new GameMap(game, context, uiStage);
        inputMultiplexer.addProcessor(map);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        super.render(delta);
        camera.update();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);

        map.draw(shapeRenderer, spriteBatch, delta);
        map.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }


}
