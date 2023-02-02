package octi.growth.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import octi.growth.Growth;
import octi.growth.input.ChangeScreenEvent;
import octi.growth.input.SaveMapEvent;
import octi.growth.input.SelectCellTypeEvent;

public class MapEditorScreen extends AbstractScreen{
    Stage uiStage;

    public MapEditorScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {
        uiStage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ChangeScreenEvent(game, ScreenType.MAIN_MENU));

        SelectBox cellPicker = new SelectBox(uiSkin);
        Array<String> items = new Array<>();
        items.add("Large Cell");
        items.add("Medium Cell");
        items.add("Small Cell");
        cellPicker.setItems(items);
        cellPicker.addListener(new SelectCellTypeEvent());

        SelectBox teamPicker = new SelectBox(uiSkin);
        Array<String> teams = new Array<>();
        teams.add("Red");
        teams.add("Green");
        teams.add("Yellow");
        teams.add("Neutral");
        teamPicker.setItems(teams);


        TextButton saveButton = new TextButton("Export", uiSkin);
        saveButton.addListener(new SaveMapEvent());

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().width(100).pad(5);

        table.add(backButton).expandX().left();
        table.add(cellPicker).width(120);
        table.add(teamPicker).width(120);
        table.add(saveButton).expandX().right();

        table.top().left();

        uiStage.addActor(table);

        Gdx.input.setInputProcessor(uiStage);

        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
