package octi.growth.screen;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import octi.growth.Growth;
import octi.growth.ecs.system.GraphicSystem;

import javax.swing.*;

public class ECSScreen extends AbstractScreen {
    PooledEngine engine;
    SpriteBatch batch;

    public ECSScreen(Growth game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        engine = new PooledEngine();
        batch = new SpriteBatch();

        engine.addSystem(new GraphicSystem(batch));

        //Add here the entities;
        //engine.addEntity();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        engine.update(delta);
    }
}
