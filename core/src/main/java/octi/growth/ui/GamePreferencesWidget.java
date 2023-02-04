package octi.growth.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;
import octi.growth.screen.GameplayScreenContext;
import octi.growth.screen.ScreenType;

public class GamePreferencesWidget extends WidgetGroup {
    private Growth game;
    private GameplayScreenContext context;

    private SelectBox<String> difficultyBox;
    private SelectBox<String> mapNameBox;
    private SelectBox<String> playerColorBox;
    private CheckBox aiBrawl;
    private TextButton backButton;
    private TextButton playButton;


    public GamePreferencesWidget(Skin uiSkin, Growth game){
        //TODO: Idea, add here a window, will make everything visible.
        this.game = game;
        context = new GameplayScreenContext();

        Table table = new Table();
        table.defaults().width(100);
        table.setFillParent(true);

        difficultyBox = new SelectBox(uiSkin);
        difficultyBox.setItems("Easy", "Medium", "Hard");

        mapNameBox = new SelectBox(uiSkin);
        mapNameBox.setItems("Tutorial");

        playerColorBox = new SelectBox(uiSkin);
        playerColorBox.setItems("Red", "Green", "Cyan", "Yellow", "Orange");

        aiBrawl = new CheckBox("AI Battle Royal", uiSkin);

        backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ChangeScreenEvent(game, ScreenType.MAIN_MENU));
        playButton = new TextButton("Start", uiSkin);

        table.add(new Label("Game Preferences", uiSkin)).row();
        table.add(new Label("Select Difficulty:", uiSkin));
        table.add(difficultyBox).row();
        table.add(new Label("Select map:", uiSkin));
        table.add(mapNameBox).row();
        table.add(new Label("Select color:", uiSkin));
        table.add(playerColorBox).row();
        table.add(aiBrawl).row();
        table.add(backButton).left();
        table.add(playButton).right();

        addActor(table);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateContext(){
        //context.setMapName();
    }
}
