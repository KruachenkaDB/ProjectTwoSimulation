package action;

import entity.Creature;
import map.Map;

import java.util.List;

public class DecreaseHungerAction implements TurnAction {
    @Override
    public void execute(Map map, int turnCounter) {
        List<Creature> creatures = map.getCreatures();
        for (Creature creature : creatures) {
            creature.decreaseHunger();
            if (creature.getHealth() <= 0) {
                map.removeEntity(creature.getCoordinates());
            }
        }
    }
}
