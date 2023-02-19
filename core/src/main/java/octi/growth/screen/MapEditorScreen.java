package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;
import octi.growth.input.SelectionEvent;
import octi.growth.model.Cell;
import octi.growth.model.CellType;
import octi.growth.model.MapModel;
import octi.growth.model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MapEditorScreen extends AbstractScreen implements InputProcessor {
    private Stage uiStage;

    private String cellSize;
    private String team;

    private SelectBox<String> cellPicker;
    private SelectBox<String> teamPicker;
    private Window window;

    private ArrayList<Cell> generatedCells;

    private ShapeRenderer shapeRenderer;

    private Cell ghostCell;


    public MapEditorScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();

        generatedCells = new ArrayList<>();

        uiStage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ChangeScreenEvent(game, ScreenType.MAIN_MENU));

        cellPicker = new SelectBox<>(uiSkin);
        Array<String> items = new Array<>();
        items.add("Large Cell");
        items.add("Medium Cell");
        items.add("Small Cell");
        cellPicker.setItems(items);
        cellPicker.addListener(new SelectionEvent(cellSize));

        teamPicker = new SelectBox<>(uiSkin);
        Array<String> teams = new Array<>();
        teams.add("Red");
        teams.add("Green");
        teams.add("Yellow");
        teams.add("Cyan");
        teams.add("Orange");
        teams.add("Neutral");
        teamPicker.setItems(teams);
        teamPicker.addListener(new SelectionEvent(team));


        TextButton saveButton = new TextButton("Export", uiSkin);
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setVisible(true);
            }
        });

        window = new Window("Export", uiSkin);

        TextField mapName = new TextField("Map Name", uiSkin);

        window.add(mapName).center().expandX();
        window.row();

        TextButton windowCancelButton = new TextButton("Cancel", uiSkin);
        windowCancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setVisible(false);
            }
        });

        TextButton windowExportButton = new TextButton("Export", uiSkin);
        windowExportButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                window.setVisible(false);
                Gdx.app.log("EXPORT", "Export clicked");
                MapModel mapModel = new MapModel(generatedCells);

                Json json = new Json();
                mapModel.setMapName(mapName.getText());
                String jsonMap = json.toJson(mapModel);

                Gdx.app.log("EXPORT", json.prettyPrint(jsonMap));

                String mapNameStr = mapName.getText();
                mapNameStr = mapNameStr.toLowerCase(Locale.ROOT);
                mapNameStr = mapNameStr.replace(" ", "_");

                FileHandle fh = Gdx.files.local("assets/maps/" + mapNameStr + ".json");
                json.setOutputType(JsonWriter.OutputType.json);
                fh.writeString(json.prettyPrint(jsonMap), false);
            }
        });

        window.add(windowCancelButton).expandX().bottom().left();
        window.add(windowExportButton).expandX().bottom().right();
        window.add().expandX().bottom().right();
        window.setVisible(false);
        window.pack();

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(100).pad(5);

        table.add(backButton).expandX().left();
        table.add(cellPicker).width(120);
        table.add(teamPicker).width(120);
        table.add(saveButton).expandX().right();
        table.row().row();
        table.add(window).center().width(200).height(200);

        table.top().left();

        uiStage.addActor(table);

        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        ghostCell = new Cell(new Vector2(0, 0), CellType.LARGE_CELL, Team.RED);


        super.show();
    }

    public void update() {
        team = teamPicker.getSelected();
        cellSize = cellPicker.getSelected();

        ghostCell.setTeam(getTeam(team));
        ghostCell.setType(getType(cellSize));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (!window.isVisible()) {
            ghostCell.drawGhost(shapeRenderer);
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        generatedCells.forEach(c -> c.drawCell(shapeRenderer));
        shapeRenderer.end();

        uiStage.act(delta);
        uiStage.draw();
    }

    private Team getTeam(String color) {
        switch (color) {
            case "Red":
                return Team.RED;
            case "Green":
                return Team.GREEN;
            case "Yellow":
                return Team.YELLOW;
            case "Cyan":
                return Team.CYAN;
            case "Orange":
                return Team.ORANGE;
            case "Neutral":
                return Team.NEUTRAL;
            default:
                throw new IllegalArgumentException("Invalid Color!");
        }
    }

    private CellType getType(String type) {
        switch (type) {
            case "Large Cell":
                return CellType.LARGE_CELL;
            case "Medium Cell":
                return CellType.MEDIUM_CELL;
            case "Small Cell":
                return CellType.SMALL_CELL;
            default:
                throw new IllegalArgumentException("In existent Cell");
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        uiStage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
        int revertedY = Gdx.graphics.getHeight() - screenY;
        Vector2 touchPoint = new Vector2(screenX, revertedY);

        if (revertedY < Gdx.graphics.getHeight() * 0.85 && !window.isVisible()) {
            if (button == Input.Buttons.LEFT) {
                //Place a Cell.
                Cell newCell = new Cell(touchPoint, ghostCell.getType(), ghostCell.getTeam());

                //Validate placement.
                boolean isCellOverlapping = generatedCells.stream().anyMatch(cell -> cell.getCollisionCircle().overlaps(newCell.getCollisionCircle()));
                if (isCellOverlapping) {
                    Gdx.app.log("ERROR", "Cannot place cell, it overlaps!");
                } else {
                    generatedCells.add(newCell);
                }
            }
        }

        if (button == Input.Buttons.RIGHT) {
            List<Cell> markedCells = generatedCells.stream().filter(cell -> cell.getCollisionCircle().contains(touchPoint)).collect(Collectors.toList());
            generatedCells.removeAll(markedCells);
            //Delete a Cell.
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int revertedY = Gdx.graphics.getHeight() - screenY;

        //Check for upper bounds.
        if (revertedY < Gdx.graphics.getHeight() * 0.85) {
            ghostCell.setPosition(new Vector2(screenX, revertedY));
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
