package octi.growth.ai.task;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import octi.growth.ai.Agent;

public class ReinforceTask extends LeafTask<Agent> {
    @Override
    public Status execute() {
        Agent agent = getObject();
        agent.reinforce();
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Agent> copyTo(Task<Agent> task) {
        return null;
    }
}
