package action;

import entity.Creature;
import map.GameMap;

import java.util.List;

public class DecreaseHungerAction implements Action {
    @Override
    public void execute(GameMap gameMap) {
        List<Creature> creatures = gameMap.getEntitiesBy(Creature.class);
        for (Creature creature : creatures) {
            creature.decreaseHunger();
            if (creature.getHealth() <= 0) {
                gameMap.removeEntity(creature.getCoordinates());
            }
        }
    }
}
