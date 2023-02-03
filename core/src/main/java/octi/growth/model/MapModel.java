package octi.growth.model;

import java.util.List;

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
}
