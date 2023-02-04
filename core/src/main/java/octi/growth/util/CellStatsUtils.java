package octi.growth.util;

import octi.growth.model.CellType;
import octi.growth.model.Team;

public class CellStatsUtils {
    public static Team getTeam(String color){
        switch (color){
            case "Red": return Team.RED;
            case "Green": return Team.GREEN;
            case "Yellow": return Team.YELLOW;
            case "Cyan": return Team.CYAN;
            case "Orange": return Team.ORANGE;
            case "Neutral": return Team.NEUTRAL;
            default: throw new IllegalArgumentException("Invalid Color!");
        }
    }

    public static CellType getType(String type){
        switch (type){
            case "Large Cell": return CellType.LARGE_CELL;
            case "Medium Cell": return CellType.MEDIUM_CELL;
            case "Small Cell": return CellType.SMALL_CELL;
            default: throw new IllegalArgumentException("In existent Cell");
        }
    }
}
