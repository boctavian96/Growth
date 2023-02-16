package octi.growth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class MapModel {
    private String mapName;
    private ArrayList<Cell> cellList;

    public MapModel(ArrayList<Cell> cellList) {
        this.cellList = cellList;
    }

    public void changePlayerColor(Team team) {
        Optional<Cell> cell = cellList.stream().filter(c -> c.getTeam().equals(team)).findAny();
        boolean hasAIDesiredColor = cell.isPresent();

        if (hasAIDesiredColor) {
            //Change AI Color to the player's color.
            List<Cell> aiColor = cellList.stream().filter(c -> c.getTeam().equals(team)).collect(Collectors.toList());
            List<Cell> playerColor = cellList.stream().filter(c -> c.getTeam().equals(Team.RED)).collect(Collectors.toList());

            aiColor.forEach(c -> c.setTeam(Team.RED));
            playerColor.forEach(c -> c.setTeam(team));
            return;
        }

        //Change player color to the desired color.
        cellList.stream().filter(c -> c.getTeam().equals(Team.RED)).forEach(c -> c.setTeam(team));
    }
}
