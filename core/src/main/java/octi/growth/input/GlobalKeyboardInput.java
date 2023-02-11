package octi.growth.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import octi.growth.Growth;

public class GlobalKeyboardInput implements InputProcessor {
    private final Growth game;
    private final Camera camera;

    public GlobalKeyboardInput(Growth game, Camera camera) {
        this.game = game;
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        /*
        if(keycode == Input.Keys.A){
            camera.translate(-5, 0, 0);
        }

        if(keycode == Input.Keys.D){
            camera.translate(5, 0, 0);
        }

        if(keycode == Input.Keys.W){
            camera.translate(0, -5, 0);
        }

        if(keycode == Input.Keys.S){
            camera.translate(0, 5, 0);
        }
         */
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.Q) {
            Gdx.app.exit();
        }

        //Remove comments when developing.
//        if (keycode == Input.Keys.F) {
//            game.changeDebug();
//        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
