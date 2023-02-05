package octi.growth.ai.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import octi.growth.ai.Agent;
import octi.growth.ai.AgentWorld;
import octi.growth.model.Cell;
import octi.growth.model.Team;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AttackTask extends LeafTask<Agent> {
    @Override
    public Status execute() {
        Agent agent = getObject();
        AgentWorld world = agent.getWorld();
        Team team = agent.getTeam();

        Random random = new Random();

        //Select a random cell that agent owns.
        List<Cell> ownedCells = world.getMapCells().stream().filter(cell -> cell.getTeam().equals(team)).collect(Collectors.toList());
        int source = 0;
        if(ownedCells.size() > 1){
            source = random.nextInt(ownedCells.size());
        }

        //Select a random cell that he dont owns.
        List<Cell> randomCells = world.getMapCells().stream().filter(cell -> !cell.getTeam().equals(team)).collect(Collectors.toList());
        if(randomCells.isEmpty()){
            //Cannot attack anything...
            return Status.FAILED;
        }else{
            int target = random.nextInt(randomCells.size());
            agent.attack(ownedCells.get(source), randomCells.get(target));
            return Status.SUCCEEDED;
        }


    }

    @Override
    protected Task<Agent> copyTo(Task<Agent> task) {
        return null;
    }
}
