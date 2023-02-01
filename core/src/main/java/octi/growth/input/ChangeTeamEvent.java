package octi.growth.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChangeTeamEvent extends ClickListener {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.log("TODO", "Implement me! ChangeTeamEvent");
        super.clicked(event, x, y);
    }
}
