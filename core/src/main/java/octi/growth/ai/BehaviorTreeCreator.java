package octi.growth.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.Reader;

public class BehaviorTreeCreator {
    public static BehaviorTree<Agent> createBehaviorTree(String policy, String agentName){
        Reader reader = null;

        try {
            reader = Gdx.files.internal(policy).reader();
            BehaviorTreeParser<Agent> parser = new BehaviorTreeParser<>();
            BehaviorTree<Agent> tree = parser.parse(reader, new Agent(agentName));
            return tree;
        } finally {
            StreamUtils.closeQuietly(reader);
        }
    }
}
