package octi.growth.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import octi.growth.model.CellType;
import octi.growth.model.Team;

public class MovementGroup {

    private boolean isAlive;
    private Team team;
    private int strength;
    private Rectangle collisionRectangle;
    private float speed;
    private int damage;
    private Vector2 position;
    private Cell target;

    public MovementGroup(Team team, int size, Vector2 source, Cell target){
        this.isAlive = true;
        this.team = team;
        this.strength = size;
        this.collisionRectangle = new Rectangle(source.x, source.y, 2, 2);
        this.position = new Vector2(source.x, source.y);
        this.target = target;
        this.speed = 20f;
    }

    public void update(float delta){
        if(!collisionRectangle.overlaps(target.getCollisionRectangle())){
            //Move to the target.
            moveTo(target.getPosition(), delta);
        }else {
            //Hit target.
            if (isAlive) {
                if (target.getTeam().equals(team)) {
                    target.setResources(target.getResources() + strength);
                } else {
                    if (target.getResources() - strength < 0) {
                        strength = strength - target.getResources();
                        target.setTeam(team);
                        target.setResources(strength);
                    } else {
                        target.setResources(target.getResources() - strength);
                    }
                }
            }
            isAlive = false;
        }
    }

    public void draw(ShapeRenderer sr){
        //TODO: Remove hard coding.
        if(isAlive()) {
            sr.setColor(team.color);
            sr.rect(position.x, position.y, 10, 10);
        }
    }

    private void moveTo(Vector2 target, float delta){
        if(position.x > target.x){
            position.x -= speed * delta;
        }else{
            position.x += speed * delta;
        }

        if(position.y > target.y){
            position.y -= speed * delta;
        }else{
            position.y += speed * delta;
        }

        collisionRectangle.setX(position.x);
        collisionRectangle.setY(position.y);
    }

    public boolean isAlive() {
        return isAlive;
    }
}
