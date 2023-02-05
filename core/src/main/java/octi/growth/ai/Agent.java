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
    public String name;
    public String brainLog;

    private BehaviorTree<Agent> behaviorTree;
    private AgentWorld world;
    private float timer;

    //Hardcode for testing purpose;
    private Team team;

    //Can this be remade???
    private GameMap api;

    List<MovementGroup> movementGroups;

    public Agent(String name){
        this(name, null, null, null, null);
    }

    public Agent(String name, BehaviorTree<Agent> behaviorTree, AgentWorld world, GameMap map, Team agentTeam){
        this.name = name;
        this.behaviorTree = behaviorTree;
        if(Objects.nonNull(behaviorTree)){
            this.behaviorTree.setObject(this);
        }
        this.world = world;
        this.api = map;
        movementGroups = new ArrayList<>();
        this.team = agentTeam;
    }

    public void update(float delta, List<Cell> mapCells, List<MovementGroup> movementGroups){
        behaviorTree.step();
        timer += delta;
        world.setMapCells(mapCells);
        world.setMovementGroups(movementGroups);
    }

    public List<MovementGroup> fetch(){
        List<MovementGroup> movementGroupsCopy = new ArrayList<>(movementGroups);
        movementGroups = new ArrayList<>();
        return movementGroupsCopy;
    }

    public void idle(){
        if(timer > 2) {
            //Agent never waits...
            log("Im waiting.");
            timer = 0;
        }
    }

    public void reinforce(){
        if(timer > 2) {
            log("Im reinforcing");
            timer = 0;

            //Select a random cell that agent owns.
            List<Cell> ownedCells = world.getMapCells().stream().filter(cell -> cell.getTeam().equals(team)).collect(Collectors.toList());
            if(ownedCells.size() == 1){
                //Nothing to reinforce...
                return;
            }else{
                //Select random source
                Random random = new Random();
                int source = random.nextInt(ownedCells.size());
                int target = random.nextInt(ownedCells.size());

                if(source == target){
                    //Failed to reinforce.
                    return;
                }

                movementGroups.add(api.spawnMovementGroup(ownedCells.get(source), ownedCells.get(target)));
            }
        }
    }

    public void attack(){
        if(timer > 2) {
            log("Im attacking");
            timer = 0;

            Random random = new Random();

            //Select a random cell that agent owns.
            List<Cell> ownedCells = world.getMapCells().stream().filter(cell -> cell.getTeam().equals(team)).collect(Collectors.toList());
            int source = 0;
            if(ownedCells.size() > 2){
                source = random.nextInt(ownedCells.size());
            }

            //Select a random cell that he dont owns.
            List<Cell> randomCells = world.getMapCells().stream().filter(cell -> !cell.getTeam().equals(team)).collect(Collectors.toList());
            int target = random.nextInt(randomCells.size());

            //Spawn a attack to a random cell that he dont owns.
            MovementGroup mg = api.spawnMovementGroup(ownedCells.get(source), randomCells.get(target));
            movementGroups.add(mg);
        }
    }

    public void log(String msg){
        GdxAI.getLogger().info("Agent: " + name, msg);
    }
}
