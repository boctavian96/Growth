package octi.growth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMap implements InputProcessor {

    boolean debugMode = false;
    BitmapFont debugFont;
    List<Cell> cells;
    float time = 0;
    float cellTime = 0;
    int fps = 0;

    private Cell selectedCell;
    private Cell destinationCell;

    public GameMap(){
        Cell redCell = new Cell(new Vector2(25, 100), CellType.MEDIUM_CELL, Team.RED);
        Cell greenCell = new Cell(new Vector2(200, 200), CellType.SMALL_CELL, Team.GREEN);
        Cell yellowCell = new Cell(new Vector2(450, 300), CellType.LARGE_CELL, Team.YELLOW);

        debugFont = new BitmapFont();

        cells = new ArrayList<>();
        cells.add(redCell);
        cells.add(greenCell);
        cells.add(yellowCell);
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, float dt){
        //Draw Paths
        drawPaths(cells, shapeRenderer);

        //Draw Cells
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        cells.forEach(cell -> cell.drawCell(shapeRenderer));
        shapeRenderer.end();

        //Draw Resources.
        batch.begin();
        cells.forEach(cell -> cell.drawResources(batch));
        batch.end();

        //Draw Debug
        if(debugMode) {
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
            cells.forEach(cell -> cell.update());
            cellTime = 0;
        }
    }

    private void spawnAttack(){

    }

    private void drawPaths(List<Cell> cells, ShapeRenderer sr){
        for(int i = 0; i<cells.size()-1; i++){
            drawDotedLine(sr, 5, cells.get(i).position, cells.get(i+1).position);
        }
    }

    private void drawDotedLine(ShapeRenderer sr, int dotDistance, Vector2 startPosition, Vector2 destinationPosition){
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
        if(keycode == Input.Keys.Q){
            Gdx.app.exit();
        }
        if(keycode == Input.Keys.D){
            debugMode = !debugMode;
        }
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
                if(button == Input.Buttons.LEFT) {
                    Gdx.app.log("Selected Cell", "A cell has been selected");
                    selectedCell = cell;
                    return false;
                }
                if(button == Input.Buttons.RIGHT){
                    if(Objects.isNull(selectedCell) || selectedCell.equals(destinationCell)){
                        destinationCell = null;
                        return false;
                    }else{
                        //Spawn Attack
                        destinationCell = cell;

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
