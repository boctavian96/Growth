package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import octi.growth.Constants;
import octi.growth.Growth;
import octi.growth.input.GlobalKeyboardInput;

public abstract class AbstractScreen implements Screen {
    protected final Growth game;
    protected final InputMultiplexer inputMultiplexer;

    public AbstractScreen(Growth game) {
        this.game = game;
        this.inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new GlobalKeyboardInput(game));
    }

    public void changeScreen(Screen newScreen) {
        game.setScreen(newScreen);
        dispose();
    }

    protected Skin loadSkin() {
        return new Skin(Gdx.files.internal(Constants.UI_SKIN));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
