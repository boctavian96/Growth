package octi.growth.screen;

import octi.growth.model.Team;

public class GameplayScreenContext {
    private String mapName;
    private Team playerTeam;
    private String aiDifficulty;
    private boolean aiBattle;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Team getPlayerTeam() {
        return playerTeam;
    }

    public void setPlayerTeam(Team playerTeam) {
        this.playerTeam = playerTeam;
    }

    public String getAiDifficulty() {
        return aiDifficulty;
    }

    public void setAiDifficulty(String aiDifficulty) {
        this.aiDifficulty = aiDifficulty;
    }

    public boolean isAiBattle() {
        return aiBattle;
    }

    public void setAiBattle(boolean aiBattle) {
        this.aiBattle = aiBattle;
    }

    public static GameplayScreenContext tutorialContext() {
        GameplayScreenContext ctx = new GameplayScreenContext();
        ctx.setAiBattle(false);
        ctx.setPlayerTeam(Team.RED);
        ctx.setAiDifficulty("None");
        ctx.setAiBattle(false);
        ctx.setMapName("tutorial.json");
        return ctx;
    }
}
