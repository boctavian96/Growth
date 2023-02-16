package octi.growth.input;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SelectionEvent extends ChangeListener {
    private String objReference;

    public SelectionEvent(String select) {
        this.objReference = select;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        objReference = ((SelectBox<String>) actor).getSelected();
    }
}
