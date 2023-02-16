package octi.growth.ai;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import octi.growth.model.Cell;
import octi.growth.model.GameMap;
import octi.growth.model.MovementGroup;
import octi.growth.model.Team;

import java.util.*;
import java.util.stream.Collectors;

public class Agent {
    public final String name;
    public String brainLog;

    private final BehaviorTree<Agent> behaviorTree;
    private final AgentWorld world;
    private float timer;
    private boolean isAlive;

    //Hardcode for testing purpose;
    private final Team team;

    //Can this be remade???
    private final GameMap api;

    List<MovementGroup> movementGroups;

    public Agent(String name) {
        this(name, null, null, null, null);
    }

    public Agent(String name, BehaviorTree<Agent> behaviorTree, AgentWorld world, GameMap map, Team agentTeam) {
        this.name = name;
        this.behaviorTree = behaviorTree;
        if (Objects.nonNull(behaviorTree)) {
            this.behaviorTree.setObject(this);
        }
        this.world = world;
        this.api = map;
        movementGroups = new ArrayList<>();
        this.team = agentTeam;
    }

    public void update(float delta, List<Cell> mapCells, List<MovementGroup> movementGroups) {
        isAlive = checkAlive();
        if (isAlive) {
            behaviorTree.step();
            timer += delta;
            world.setMapCells(mapCells);
            world.setMovementGroups(movementGroups);
        }
    }

    public List<MovementGroup> fetch() {
        List<MovementGroup> movementGroupsCopy = new ArrayList<>(movementGroups);
        movementGroups = new ArrayList<>();
        return movementGroupsCopy;
    }

    public void idle() {
        if (timer > 2) {
            //Agent never waits...
            log("Im waiting.");
            timer = 0;
        }
    }

    public void reinforce() {
        if (timer > 2) {
            log("Im reinforcing");
            timer = 0;

            //Select a random cell that agent owns.
            List<Cell> ownedCells = world.getMapCells().stream().filter(cell -> cell.getTeam().equals(team)).collect(Collectors.toList());
            if (ownedCells.size() > 1) {
                //Select random source
                Random random = new Random();
                int source = random.nextInt(ownedCells.size());
                int target = random.nextInt(ownedCells.size());

                if (source == target) {
                    //Failed to reinforce.
                    return;
                }

                movementGroups.add(api.spawnMovementGroup(ownedCells.get(source), ownedCells.get(target)));
            }
        }
    }

    public void attack(Cell source, Cell target) {
        if (timer > 2) {
            log("Im attacking");
            timer = 0;

            //Spawn an attack to a random cell that he don't own.
            MovementGroup mg = api.spawnMovementGroup(source, target);
            movementGroups.add(mg);
        }
    }

    private boolean checkAlive() {
        return world.getMapCells().stream().anyMatch(cell -> cell.getTeam().equals(team));
    }

    public AgentWorld getWorld() {
        return this.world;
    }

    public Team getTeam() {
        return this.team;
    }

    public void log(String msg) {
        GdxAI.getLogger().info("Agent: " + name, msg);
    }
}
