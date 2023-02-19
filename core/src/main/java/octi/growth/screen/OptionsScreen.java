package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;

import static octi.growth.Constants.*;

public class OptionsScreen extends AbstractScreen {

    private Stage stage;
    private Preferences preferences;

    public OptionsScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage();

        preferences = Gdx.app.getPreferences(PREFERENCES);

        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Skin uiSkin = loadSkin();

        Slider musicSlider = new Slider(0f, 1f, 0.05f, false, uiSkin);
        musicSlider.setVisualPercent(preferences.getFloat(MUSIC_VOLUME));
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.putFloat(MUSIC_VOLUME, musicSlider.getValue());
                game.setMusicVolume(musicSlider.getValue());
            }
        });


        Slider soundSlider = new Slider(0f, 1f, 0.05f, false, uiSkin);
        soundSlider.setVisualPercent(preferences.getFloat(SOUND_VOLUME));
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                preferences.putFloat(SOUND_VOLUME, soundSlider.getValue());
            }
        });

        TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ChangeScreenEvent(game, ScreenType.MAIN_MENU));
        TextButton saveButton = new TextButton("Save", uiSkin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                preferences.flush();
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("Music Volume: ", uiSkin));
        table.add(musicSlider).row();
        table.add(new Label("Sound Volume: ", uiSkin));
        table.add(soundSlider).row();
        table.add(backButton).width(80f).space(10f);
        table.add(saveButton).width(80f).space(10f);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.draw();
        stage.act(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.F5)) {
            refresh();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private void refresh() {
        dispose();
        show();
    }
}
