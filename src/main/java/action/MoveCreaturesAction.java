package action;

import entity.Creature;
import map.GameMap;

import java.util.List;

public class MoveCreaturesAction implements Action {
    @Override
    public void execute(GameMap gameMap) {
        List<Creature> creatures = gameMap.getEntitiesBy(Creature.class);

        for (Creature creature : creatures) {
            creature.makeMove(gameMap);
        }
    }
}
