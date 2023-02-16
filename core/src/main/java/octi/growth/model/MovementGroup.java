package octi.growth.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import octi.growth.Constants;

import static octi.growth.Constants.*;

public class MovementGroup {

    private boolean isAlive;
    private Team team;
    private int strength;
    private Circle collisionCircle;
    private float speed;
    private int damage;
    private Vector2 position;
    private Cell target;
    Sound sound = Gdx.audio.newSound(Gdx.files.internal(Constants.LOSE_SOUND));


    public MovementGroup(Team team, int size, Vector2 source, Cell target) {
        this.isAlive = true;
        this.team = team;
        this.strength = size;
        this.collisionCircle = new Circle(source.x, source.y, size);
        this.position = new Vector2(source.x, source.y);
        this.target = target;
        this.speed = 20f;
    }

    public void update(float delta) {
        if (!collisionCircle.overlaps(target.getCollisionCircle())) {
            //Move to the target.
            moveTo(target.getPosition(), delta);
        } else {
            //Hit target.
            if (isAlive) {
                if (target.getTeam().equals(team)) {
                    target.setResources(target.getResources() + strength);
                } else {
                    if (target.getResources() - strength < 0) {
                        //Cell changes owner.
                        playSound();
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

    private void playSound() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES);
        boolean isSoundMuted = preferences.getBoolean(MUTE_SOUND);

        if (!isSoundMuted) {
            float soundVolume = preferences.getFloat(SOUND_VOLUME);
            sound.play(soundVolume);
        }
    }

    public void draw(ShapeRenderer sr) {
        //TODO: Remove hard coding.
        if (isAlive()) {
            sr.setColor(team.color);
            sr.rect(position.x, position.y, 10, 10);
        }
    }

    private void moveTo(Vector2 target, float delta) {
        if (position.x > target.x) {
            position.x -= speed * delta;
        } else {
            position.x += speed * delta;
        }

        if (position.y > target.y) {
            position.y -= speed * delta;
        } else {
            position.y += speed * delta;
        }

        collisionCircle.setX(position.x);
        collisionCircle.setY(position.y);
    }

    public boolean isAlive() {
        return isAlive;
    }
}
