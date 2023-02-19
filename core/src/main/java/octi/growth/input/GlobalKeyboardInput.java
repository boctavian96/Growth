package octi.growth.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import octi.growth.Growth;
import octi.growth.screen.ECSScreen;

public class GlobalKeyboardInput implements InputProcessor {
    private final Growth game;
    IntSet keysDown;

    public GlobalKeyboardInput(Growth game) {
        this.game = game;
        keysDown = new IntSet(3);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (game.isDebugMode()) {
            Gdx.app.log("DEBUG_KEY_DOWN", String.valueOf(keycode));
        }

        keysDown.add(keycode);
        if (keysDown.size >= 2) {
            handleKeyCombinations(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.Q) {
            Gdx.app.exit();
        }

        keysDown.remove(keycode);
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

    private void handleKeyCombinations(int mostRecentKey) {
        if (keysDown.contains(Input.Keys.CONTROL_LEFT) && keysDown.contains(Input.Keys.D)) {
            game.changeDebug();
            Gdx.app.log("DEBUG MODE: ", String.valueOf(game.isDebugMode()));
        }

        if (keysDown.contains(Input.Keys.CONTROL_LEFT) && keysDown.contains(Input.Keys.P)) {
            Gdx.app.log("DEBUG ECS", "Moving to the ECS Screen");
            game.setScreen(new ECSScreen(game));
        }
    }
}
