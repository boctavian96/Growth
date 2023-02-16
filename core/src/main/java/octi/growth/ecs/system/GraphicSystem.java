package octi.growth.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import octi.growth.ecs.component.GraphicComponent;
import octi.growth.ecs.component.PositionComponent;

public class GraphicSystem extends IteratingSystem {

    private final SpriteBatch batch;
    private final Array<Entity> renderQueue;

    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<GraphicComponent> gm = ComponentMapper.getFor(GraphicComponent.class);

    public GraphicSystem(SpriteBatch batch) {
        super(Family.all(PositionComponent.class, GraphicComponent.class).get());
        this.batch = batch;
        renderQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        batch.begin();
        for (var e : renderQueue) {
            var positionComponent = pm.get(e);
            var graphicComponent = gm.get(e);

            graphicComponent.sprite.setPosition(positionComponent.position.x, positionComponent.position.y);
            graphicComponent.sprite.draw(batch);
        }
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
