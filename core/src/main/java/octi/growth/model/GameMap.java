package octi.growth.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import octi.growth.Growth;
import octi.growth.screen.GameplayScreenContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMap implements InputProcessor {
    private final Growth game;
    private final BitmapFont debugFont;
    private final List<Cell> cells;
    private final List<MovementGroup> movementGroups;
    float time = 0;
    float cellTime = 0;
    int fps = 0;

    private Cell sourceCell;
    private Cell targetCell;

    Team playerTeam;

    public GameMap(Growth game, GameplayScreenContext context){

        StringBuilder mapPath = new StringBuilder("/assets/maps/");
        mapPath.append(context.getMapName());

        FileHandle fh = Gdx.files.local(mapPath.toString());
        String jsonString = fh.readString();

        playerTeam = context.getPlayerTeam();

        Json json = new Json();
        MapModel mapModel = json.fromJson(MapModel.class, jsonString);

        if(!playerTeam.equals(Team.RED)){
            mapModel.changePlayerColor(playerTeam);
        }

        cells = mapModel.getCellList();

        debugFont = new BitmapFont();

        this.game = game;
        movementGroups = new ArrayList<>();
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, float dt){
        //Draw Paths
        drawPaths(cells, shapeRenderer);

        //Draw Cells
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        cells.forEach(cell -> cell.drawCell(shapeRenderer));
        movementGroups.forEach(mg -> mg.draw(shapeRenderer));
        shapeRenderer.end();

        //Draw Resources.
        batch.begin();
        cells.forEach(cell -> cell.drawResources(batch));
        batch.end();

        //Draw Debug
        if(game.isDebugMode()) {
            time += dt;
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            cells.forEach(cell -> cell.drawDebug(shapeRenderer));
            shapeRenderer.end();

            if(time >= 1) {
                time = 0;
                fps = (int) (1/dt);
            }

            batch.begin();
            debugFont.draw(batch, "FPS: " + fps, 400, 450);
            batch.end();
        }
    }

    public void update(float dt){
        cellTime += dt;
        if(cellTime > 1){
            cells.forEach(Cell::update);
            cellTime = 0;
        }

        movementGroups.forEach(group -> group.update(dt));
        checkWin();
    }

    private boolean checkWin(){
        return false;
    }

    private MovementGroup spawnMovementGroup(Cell sourceCell, Cell targetCell){
        int resources = sourceCell.getResources();
        sourceCell.setResources(0);
        sourceCell.getTeam();

        //Create attack group.
        return new MovementGroup(sourceCell.getTeam(), resources, sourceCell.getPosition(), targetCell);
    }

    private void drawPaths(List<Cell> cells, ShapeRenderer sr){
        for(int i = 0; i<cells.size()/2; i++){
            for(int j = i + 1; j<cells.size(); j++) {
                drawDotedLine(sr, 5, cells.get(i).position, cells.get(j).position);
            }
        }
    }

    private void drawDotedLine(ShapeRenderer sr, final int dotDistance, Vector2 startPosition, Vector2 destinationPosition){
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        Vector2 start = new Vector2(startPosition);
        Vector2 end = new Vector2(destinationPosition);
        Vector2 vector2 = end.sub(start);
        float length = vector2.len();

        for(int i = 0; i < length; i+= dotDistance){
            vector2.clamp(length - i, length - i);
            sr.point(start.x + vector2.x, start.y + vector2.y, 0);
        }

        sr.end();
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
        int mouseY = Gdx.graphics.getHeight() - screenY;
        Gdx.app.log("Mouse Click", screenX + " " + mouseY);

        for(Cell cell : cells){
            if(cell.collisionRectangle.contains(screenX, mouseY)){
                if(button == Input.Buttons.LEFT && cell.getTeam().equals(playerTeam)) {
                    Gdx.app.log("Selected Cell", "A cell has been selected");
                    sourceCell = cell;
                    return false;
                }
                if(button == Input.Buttons.RIGHT){
                    if(Objects.isNull(sourceCell) || sourceCell.equals(targetCell)){
                        targetCell = null;
                        return false;
                    }else{
                        //Spawn Attack or reinforce.
                        if(sourceCell.getResources() > 0) {
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
