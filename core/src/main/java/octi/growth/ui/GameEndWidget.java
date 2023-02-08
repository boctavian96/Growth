package octi.growth.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameEndWidget extends Window {
    private int status = 0;

    TextButton back;
    TextButton restart;

    public GameEndWidget(String title, Skin skin, boolean playerWon) {
        super(title, skin);

        this.setFillParent(true);
        this.setWidth(200);
        this.setHeight(200);

        String playerStatus;
        if (playerWon) {
            playerStatus = "You Win!";
        } else {
            playerStatus = "You Lost :(";
        }

        this.add(new Label(playerStatus, skin)).center().row();

        //Add Back.
        back = new TextButton("Back", skin);
        this.add(back).bottom().left();

        //Add Restart.
        restart = new TextButton("Restart", skin);
        this.add(restart).bottom().right();

        //Add Listeners.
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                status = 1;
                setVisible(false);
            }
        });

        restart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                status = 2;
                setVisible(false);
            }
        });
    }

    public int getStatus(){
        return this.status;
    }
}
