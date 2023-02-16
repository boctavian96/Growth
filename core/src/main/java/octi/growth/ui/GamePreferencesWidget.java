package octi.growth.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;
import octi.growth.model.MapModel;
import octi.growth.screen.GameplayScreenContext;
import octi.growth.screen.ScreenType;
import octi.growth.util.CellStatsUtils;

import java.util.HashMap;
import java.util.Map;

public class GamePreferencesWidget extends WidgetGroup {
    private final Growth game;
    private final GameplayScreenContext context;

    private final SelectBox<String> difficultyBox;
    private final SelectBox<String> mapNameBox;
    private final SelectBox<String> playerColorBox;
    private final CheckBox aiBrawl;
    private final TextButton backButton;
    private final TextButton playButton;

    private final Map<String, String> maps;


    public GamePreferencesWidget(Skin uiSkin, Growth game) {
        //TODO: Idea, add here a window, will make everything visible.
        this.game = game;
        context = new GameplayScreenContext();

        maps = new HashMap<>();

        //Read map names;
        //GWT Configuration.
        FileHandle[] listOfFiles = Gdx.files.internal("maps/").list();
        if (listOfFiles.length == 0) {
            //Desktop Configuration.
            listOfFiles = Gdx.files.internal("assets/maps/").list();
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            Json json = new Json();
            json.setOutputType(JsonWriter.OutputType.json);
            MapModel gameMap = json.fromJson(MapModel.class, listOfFiles[i].readString());
            if (!gameMap.getMapName().equals("tutorial")) {
                maps.put(gameMap.getMapName(), listOfFiles[i].name());
            }
        }


        Table table = new Table();
        table.defaults().width(100);
        table.setFillParent(true);

        difficultyBox = new SelectBox(uiSkin);
        difficultyBox.setItems("Easy", "Medium", "Hard");

        mapNameBox = new SelectBox(uiSkin);
        mapNameBox.setItems(getMapNames(maps));

        playerColorBox = new SelectBox(uiSkin);
        playerColorBox.setItems("Red", "Green", "Cyan", "Yellow", "Orange");

        aiBrawl = new CheckBox("AI Battle Royal", uiSkin);

        backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ChangeScreenEvent(game, ScreenType.MAIN_MENU));
        playButton = new TextButton("Start", uiSkin);
        playButton.addListener(new ChangeScreenEvent(game, ScreenType.GAME, context));

        table.add(new Label("Game Preferences\n\n", uiSkin)).row();
        table.add(new Label("Select Difficulty:", uiSkin));
        table.add(difficultyBox).row();
        table.add(new Label("Select map:", uiSkin));
        table.add(mapNameBox).row();
        table.add(new Label("Select color:", uiSkin));
        table.add(playerColorBox).row();
        table.add(aiBrawl).row();
        table.add(backButton).left();
        table.add(playButton).right();

        addActor(table);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateContext();
    }

    public void updateContext() {
        context.setAiDifficulty(difficultyBox.getSelected());
        context.setMapName(maps.get(mapNameBox.getSelected()));
        context.setPlayerTeam(CellStatsUtils.getTeam(playerColorBox.getSelected()));
        context.setAiBattle(aiBrawl.isChecked());
    }

    private Array<String> getMapNames(Map<String, String> map) {
        Array<String> mapNames = new Array<>();
        map.forEach((k, v) -> mapNames.add(k));
        return mapNames;
    }
}
