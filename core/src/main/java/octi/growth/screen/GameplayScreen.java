package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import octi.growth.Growth;
import octi.growth.input.GlobalKeyboardInput;
import octi.growth.model.GameMap;

/** First screen of the application. Displayed after the application is created. */
public class GameplayScreen extends AbstractScreen {
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    ShapeRenderer debugRenderer;
    SpriteBatch spriteBatch;
    GameMap map;

    InputMultiplexer inputMultiplexer;

    public GameplayScreen(Growth game){
        super(game);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GlobalKeyboardInput(game));
    }

    @Override
    public void show() {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(w, h);
        camera.position.set(w/2, h/2, 0);


        // Prepare your screen here.
        shapeRenderer = new ShapeRenderer();
        debugRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        map = new GameMap(game);

        inputMultiplexer.addProcessor(map);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        super.render(delta);
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
