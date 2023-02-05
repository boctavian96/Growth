package octi.growth.ai.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import octi.growth.ai.Agent;
import octi.growth.ai.AgentWorld;

public class ReinforceTask extends LeafTask<Agent> {
    @Override
    public Status execute() {
        Agent agent = getObject();

        AgentWorld world = agent.getWorld();
        long ownedCells = world.getMapCells().stream().filter(c-> c.getTeam().equals(agent.getTeam())).count();

        //Has only one cell?
        if(ownedCells == 1){
            //Nothing to reinforce
            return Status.FAILED;
        }

        agent.reinforce();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Agent> copyTo(Task<Agent> task) {
        return null;
    }
}
