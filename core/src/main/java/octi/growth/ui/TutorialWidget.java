package octi.growth.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import octi.growth.Growth;
import octi.growth.screen.MainMenuScreen;

public class TutorialWidget extends Table {

    private final Growth game;
    private int tutorialState = 0;

    private final Window tutorialWidget;
    private final Label label;
    private final TextButton nextButton;
    private final TextButton backButton;

    public TutorialWidget(Growth game, Skin uiSkin) {
        this.game = game;
        this.setFillParent(true);

        label = new Label("Welcome to the tutorial!\n\nThe scope of the game is to grow your planetary empire by expanding.", uiSkin);
        label.setWrap(true);

        tutorialWidget = new Window("", uiSkin);
        tutorialWidget.add(label).center().width(180).colspan(2);
        tutorialWidget.row();
        nextButton = new TextButton("Next", uiSkin);
        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (tutorialState < 5) {
                    tutorialState++;
                    if (tutorialState == 5) {
                        game.setScreen(new MainMenuScreen(game));
                    }
                    setLabelState(tutorialState);
                }
            }
        });

        backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if (tutorialState > 0) {
                    tutorialState--;
                    setLabelState(tutorialState);
                }
            }
        });
        tutorialWidget.add(backButton).width(50).pad(25, 0, 0, 0);
        tutorialWidget.add(nextButton).width(50).pad(25, 0, 0, 0);

        this.add(tutorialWidget).size(200, 200).top().left().pad(30);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private void setLabelState(int tutorialState) {
        switch (tutorialState) {
            case 0:
                label.setText("Welcome to the tutorial!\n\nThe scope of the game is to grow your planetary empire by expanding.");
                break;
            case 1:
                label.setText("Tutorial #1\n\nIn this scenario you will play as the red planet.");
                break;
            case 2:
                label.setText("Tutorial #2\n\nLet's select your planet using the left mouse button!");
                break;
            case 3:
                label.setText("Tutorial #3\n\nNow let's start an attack! With your planet selected right click on the gray planet!");
                nextButton.setText("Next");
                break;
            case 4:
                label.setText("Congratulations!\n\nNow it's time to grow your empire!");
                nextButton.setText("Finish!");
                break;
        }
    }
}
