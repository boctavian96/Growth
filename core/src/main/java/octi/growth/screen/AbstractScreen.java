package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import octi.growth.Growth;

public abstract class AbstractScreen implements Screen {
    protected final Growth game;

    public AbstractScreen(Growth game){
        this.game = game;
    }

    public void changeScreen(Screen newScreen){
        game.setScreen(newScreen);
        dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Clear screen
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
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
