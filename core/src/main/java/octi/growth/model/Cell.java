package octi.growth.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cell {
    private static final BitmapFont font = new BitmapFont();

    private CellType type;
    private Team team;
    public Vector2 position;
    public Circle collisionCircle;
    private int size;
    private int resources;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

    public Cell(Vector2 position, CellType cellType, Team team) {
        this.position = position;
        this.type = cellType;
        size = cellType.size;
        resources = 0;
        this.team = team;
        collisionCircle = createCollisionCircle(position, cellType);
        selected = false;
    }

    private Circle createCollisionCircle(Vector2 position, CellType cellType) {
        int xOffset = -cellType.size + 5;
        int yOffset = -cellType.size + 5;
        return new Circle(position.x, position.y, size);
    }

    public void update() {
        boolean atMaximumCapacity = true;
        switch (type) {
            case SMALL_CELL:
                atMaximumCapacity = resources >= 20;
                break;
            case MEDIUM_CELL:
                atMaximumCapacity = resources >= 50;
                break;
            case LARGE_CELL:
                atMaximumCapacity = resources >= 80;
                break;
        }
        if (!atMaximumCapacity && !team.equals(Team.NEUTRAL)) {
            this.resources += type.growth;
        }
    }

    public void drawCell(ShapeRenderer sr) {
        sr.setColor(team.color);
        sr.circle(position.x, position.y, size);
    }

    /**
     * Draws the circle at half of alpha. Use only on the map editor.
     *
     * @param sr Shape Renderer used for debugging purposes.
     */
    public void drawGhost(ShapeRenderer sr) {
        Color ghostColor = team.color;
        ghostColor.a = 0.5f;
        sr.setColor(ghostColor);
        sr.circle(position.x, position.y, type.size);
    }

    public void drawResources(SpriteBatch batch) {
        int xOffset = -11;
        int yOffset = 5;

        font.draw(batch, String.valueOf(resources), position.x + xOffset, position.y + yOffset);
    }

    public void drawDebug(ShapeRenderer debugRenderer) {
        //Debug
        debugRenderer.setColor(Color.WHITE);
        debugRenderer.set(ShapeRenderer.ShapeType.Line);
        debugRenderer.circle(collisionCircle.x, collisionCircle.y, collisionCircle.radius);
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }

    public void setCollisionCircle(Circle collisionCircle) {
        this.collisionCircle = collisionCircle;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSize(CellType typeSize) {
        this.size = typeSize.size;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        if (resources < 0) {
            this.resources = 0;
            return;
        }
        this.resources = resources;
    }

    public boolean isSelected() {
        return selected;
    }
}
