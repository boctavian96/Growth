package octi.growth.ai;

import octi.growth.model.Cell;
import octi.growth.model.MovementGroup;

import java.util.List;

public class AgentWorld {
    List<Cell> mapCells;
    List<MovementGroup> movementGroups;

    public AgentWorld(List<Cell> mapCells, List<MovementGroup> movementGroups) {
        this.mapCells = mapCells;
        this.movementGroups = movementGroups;
    }

    public List<Cell> getMapCells() {
        return mapCells;
    }

    public void setMapCells(List<Cell> mapCells) {
        this.mapCells = mapCells;
    }

    public List<MovementGroup> getMovementGroups() {
        return movementGroups;
    }

    public void setMovementGroups(List<MovementGroup> movementGroups) {
        this.movementGroups = movementGroups;
    }
}
