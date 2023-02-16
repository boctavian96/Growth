package octi.growth.model;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Json;
import octi.growth.Growth;
import octi.growth.ai.Agent;
import octi.growth.ai.AgentWorld;
import octi.growth.ai.BehaviorTreeCreator;
import octi.growth.screen.GameplayScreenContext;
import octi.growth.screen.MainMenuScreen;
import octi.growth.ui.GameEndWidget;

import java.util.*;

import static octi.growth.Constants.EASY_AI;
import static octi.growth.Constants.UI_SKIN;

public class GameMap extends InputAdapter {
    private final GameplayScreenContext context;

    private boolean isGameFinished;

    private final Growth game;
    private final BitmapFont debugFont;
    private List<Cell> cells;
    private List<MovementGroup> movementGroups;
    private List<Agent> agents;

    float time = 0;
    float cellTime = 0;
    int fps = 0;

    private Cell sourceCell;
    private Cell targetCell;

    Team playerTeam;

    Stage uiStage;
    Skin uiSkin;
    TextButton soundButton;
    GameEndWidget gameEndWidget;

    public GameMap(Growth game, GameplayScreenContext context, Stage uiStage) {
        this.game = game;
        this.context = context;
        this.debugFont = new BitmapFont();
        this.uiStage = uiStage;
        create();
    }

    private void create() {
        this.isGameFinished = false;

        uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        soundButton = new TextButton("Mute", uiSkin);
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ("Mute".equals(soundButton.getText().toString())) {
                    soundButton.setText("Unmute");
                    Preferences preferences = Gdx.app.getPreferences("preferences");
                    preferences.putBoolean("muteMusic", true);
                    preferences.putBoolean("muteSound", true);
                    preferences.flush();
                    game.muteSound();
                } else {
                    soundButton.setText("Mute");
                    Preferences preferences = Gdx.app.getPreferences("preferences");
                    preferences.putBoolean("muteMusic", false);
                    preferences.putBoolean("muteSound", false);
                    preferences.flush();
                    game.muteSound();
                }
            }
        });

        soundButton.setPosition(700, 550);
        soundButton.setWidth(80f);

        uiStage.addActor(soundButton);

        StringBuilder mapPath = new StringBuilder("maps/");
        mapPath.append(context.getMapName());

        FileHandle fh = Gdx.files.internal(mapPath.toString());
        String jsonString = fh.readString();

        playerTeam = context.getPlayerTeam();

        Json json = new Json();
        MapModel mapModel = json.fromJson(MapModel.class, jsonString);

        if (!playerTeam.equals(Team.RED)) {
            mapModel.changePlayerColor(playerTeam);
        }

        cells = mapModel.getCellList();
        movementGroups = new ArrayList<>();

        Set<Team> availableTeams = new HashSet<>();

        if (context.isAiBattle()) {
            //All AI on the map.
            playerTeam = Team.NEUTRAL;
            for (Cell c : cells) {
                if (!c.getTeam().equals(Team.NEUTRAL)) {
                    availableTeams.add(c.getTeam());
                }
            }
        } else {
            for (Cell c : cells) {
                if (!c.getTeam().equals(Team.NEUTRAL) && !c.getTeam().equals(playerTeam)) {
                    availableTeams.add(c.getTeam());
                }
            }
        }

        agents = createAgents(availableTeams);
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, float dt) {
        //Draw Paths
        drawPaths(cells, shapeRenderer);

        //Draw Cells
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        cells.forEach(cell -> cell.drawCell(shapeRenderer));
        movementGroups.forEach(mg -> mg.draw(shapeRenderer));
        shapeRenderer.end();

        //Add debug outline to selected cell
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        cells.forEach(cell -> {
            if (cell.isSelected()) {
                cell.drawDebug(shapeRenderer);
            }
        });
        shapeRenderer.end();

        //Draw Resources.
        batch.begin();
        cells.forEach(cell -> cell.drawResources(batch));
        batch.end();
        uiStage.draw();

        //Draw Debug
        if (game.isDebugMode()) {
            time += dt;
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            cells.forEach(cell -> cell.drawDebug(shapeRenderer));
            shapeRenderer.end();

            if (time >= 1) {
                time = 0;
                fps = (int) (1 / dt);
            }

            batch.begin();
            debugFont.draw(batch, "FPS: " + fps, 400, 450);
            batch.end();
        }
    }

    public void update(float dt) {
        if (!isGameFinished) {
            cellTime += dt;
            if (cellTime > 1) {
                cells.forEach(Cell::update);
                cellTime = 0;
            }

            movementGroups.forEach(group -> group.update(dt));

            agents.forEach(a -> a.update(dt, cells, movementGroups));
            agents.forEach(a -> movementGroups.addAll(a.fetch()));
            isGameFinished = checkWin();
        }
        uiStage.act(dt);

        if (isGameFinished) {
            int status = gameEndWidget.getStatus();

            if (status == 1) {
                //Go to main menu.
                game.setScreen(new MainMenuScreen(game));
            }

            if (status == 2) {
                //Restart level.
                create();
            }
        }
    }

    private boolean checkWin() {
        if (!context.isAiBattle()) {
            //If only one color on the board it's a win.
            boolean isTheGameFinished = cells.stream().map(Cell::getTeam).distinct().limit(2).count() <= 1;

            if (isTheGameFinished) {
                if (cells.get(0).getTeam().equals(playerTeam)) {
                    gameEndWidget = new GameEndWidget("", new Skin(Gdx.files.internal(UI_SKIN)), true);
                    Gdx.app.log("Victory", "Player has won!");
                } else {
                    gameEndWidget = new GameEndWidget("", new Skin(Gdx.files.internal(UI_SKIN)), false);
                    Gdx.app.log("Loss", "Player has lost");
                }

                uiStage.addActor(gameEndWidget);
                return true;
            }
        }
        return false;
    }

    public MovementGroup spawnMovementGroup(Cell sourceCell, Cell targetCell) {
        int resources = sourceCell.getResources();
        sourceCell.setResources(0);

        //Create attack group.
        return new MovementGroup(sourceCell.getTeam(), resources, sourceCell.getPosition(), targetCell);
    }

    public void spawnMovementGroupAI(Cell sourceCell, Cell targetCell) {
        spawnMovementGroup(sourceCell, targetCell);
    }

    private void drawPaths(List<Cell> cells, ShapeRenderer sr) {
        for (int i = 0; i < cells.size() / 2; i++) {
            for (int j = i + 1; j < cells.size(); j++) {
                //drawDotedLine(sr, 5, cells.get(i).position, cells.get(j).position);
                drawLine(sr, cells.get(i).position, cells.get(j).position);
            }
        }
    }

    private void drawDotedLine(ShapeRenderer sr, final int dotDistance, Vector2 startPosition, Vector2 destinationPosition) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        Vector2 start = new Vector2(startPosition);
        Vector2 end = new Vector2(destinationPosition);
        Vector2 vector2 = end.sub(start);
        float length = vector2.len();

        for (int i = 0; i < length; i += dotDistance) {
            vector2.clamp(length - i, length - i);
            sr.point(start.x + vector2.x, start.y + vector2.y, 0);
        }

        sr.end();
    }

    private void drawLine(ShapeRenderer sr, Vector2 startPosition, Vector2 destinationPosition) {
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        sr.rectLine(startPosition, destinationPosition, 3);
        sr.end();
    }

    public List<Agent> createAgents(Set<Team> availableTeams) {
        List<Agent> agents = new ArrayList<>();
        for (Team t : availableTeams) {
            String agentName = "Agent " + Math.random();
            //BehaviorTree<Agent> behaviorTree = BehaviorTreeCreator.createBehaviorTree(EASY_AI, agentName);
            BehaviorTree<Agent> behaviorTree = BehaviorTreeCreator.createBehaviorTree(agentName);
            AgentWorld world = new AgentWorld(cells, movementGroups);
            agents.add(new Agent(agentName, behaviorTree, world, this, t));
        }
        return agents;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Cheat code, for debugging purposes.
        if (keycode == Input.Keys.K && game.isDebugMode()) {
            cells.forEach(cell -> cell.setTeam(playerTeam));
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int mouseY = Gdx.graphics.getHeight() - screenY;
        Gdx.app.log("Mouse Click", screenX + " " + mouseY);

        for (Cell cell : cells) {
            if (cell.collisionCircle.contains(screenX, mouseY)) {
                if (button == Input.Buttons.LEFT && cell.getTeam().equals(playerTeam)) {
                    Gdx.app.log("Selected Cell", "A cell has been selected");
                    //Set last clicked cell to selected, deselect previous selected cell
                    if (sourceCell != null) {
                        cells.stream()
                            .filter(Cell::isSelected).findFirst()
                            .ifPresent(playerCell -> playerCell.setSelected(false));
                    }
                    cell.setSelected(true);
                    sourceCell = cell;
                    return false;
                }
                if (button == Input.Buttons.RIGHT) {
                    if (Objects.isNull(sourceCell) || sourceCell.equals(targetCell)) {
                        targetCell = null;
                        return false;
                    } else {
                        //Spawn Attack or reinforce.
                        if (sourceCell.getResources() > 0) {
                            targetCell = cell;
                            movementGroups.add(spawnMovementGroup(sourceCell, targetCell));
                            Gdx.app.log("MOVE", "Spawned movement group");
                        }
                    }
                }
                return false;
            }
        }

        return false;
    }
}
