package octi.growth;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


enum CellType{
    SMALL_CELL(15, 1),
    MEDIUM_CELL(25, 2),
    LARGE_CELL(35, 3);

    public final int size;
    public final int growth;

    private CellType(int size, int growth){
        this.size = size;
        this.growth = growth;
    }
}

enum Team{
    RED(Color.RED),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW);

    public Color color;

    private Team(Color color){
        this.color = color;
    }
}

public class Cell {
    private static final BitmapFont font = new BitmapFont();

    private CellType type;
    private Team team;
    public Vector2 position;
    public Rectangle collisionRectangle;
    private int size;
    private int resources;


    public Cell(Vector2 position, CellType cellType, Team team){
        this.position = position;
        this.type = cellType;
        size = cellType.size;
        resources = 0;
        this.team = team;
        collisionRectangle = createCollisionRectangle(position, cellType);

    }

    private Rectangle createCollisionRectangle(Vector2 position, CellType cellType){
        int xOffset = -cellType.size + 5;
        int yOffset = -cellType.size + 5;

        return new Rectangle(position.x + xOffset, position.y + yOffset, cellType.size * 2 - 10, cellType.size * 2 - 10);
    }

    public void update(){
        this.resources += type.growth;
    }

    public void drawCell(ShapeRenderer sr){
        sr.setColor(team.color);
        sr.circle(position.x, position.y, size);


    }

    public void drawResources(SpriteBatch batch){
        int xOffset = -11;
        int yOffset = 5;

        font.draw(batch, String.valueOf(resources), position.x + xOffset, position.y + yOffset);
    }

    public void drawDebug(ShapeRenderer debugRenderer){
        //Debug
        debugRenderer.setColor(Color.WHITE);
        debugRenderer.set(ShapeRenderer.ShapeType.Line);
        debugRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
    }
}
