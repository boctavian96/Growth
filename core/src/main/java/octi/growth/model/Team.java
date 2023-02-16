package octi.growth.model;

import com.badlogic.gdx.graphics.Color;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Team {
    RED(Color.RED),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    CYAN(Color.CYAN),
    ORANGE(Color.ORANGE),
    NEUTRAL(Color.GRAY);

    public final Color color;
}
