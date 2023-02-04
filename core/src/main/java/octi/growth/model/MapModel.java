package octi.growth.model;

import java.util.List;
import java.util.Optional;

public class MapModel {
    private String mapName;
    private List<Cell> cellList;

    public MapModel(){

    }

    public MapModel(List<Cell> cellList){
        this.cellList = cellList;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public void changePlayerColor(Team team){
        Optional<Cell> cell = cellList.stream().filter(c -> c.getTeam().equals(team)).findAny();
        boolean hasAIDesiredColor = cell.isPresent();

        if(hasAIDesiredColor){
            //Change AI Color to a random color.
            throw new IllegalArgumentException("AI has this Team! Please implement a team changer :D");
        }

        //Change player color to the desired color.
        cellList.stream().filter(c -> c.getTeam().equals(Team.RED)).forEach(c -> c.setTeam(team));
    }
}
