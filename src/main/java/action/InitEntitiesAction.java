package action;

import config.GameSettings;
import map.Map;
import spawn.*;

public class InitEntitiesAction implements InitAction {
    @Override
    public void execute(Map map) {
        if (!map.hasEnoughSpace()) {
//            IllegalStateException - недопустимое состояние
            throw new IllegalStateException("На поле недостаточно места для всех объектов");
        }

        for (int i = 0; i < GameSettings.INITIAL_PREDATOR_COUNT; i++) {
            new PredatorSpawnAction().execute(map);
        }

        for (int i = 0; i < GameSettings.INITIAL_HERBIVORE_COUNT; i++) {
            new HerbivoreSpawnAction().execute(map);
        }

        for (int i = 0; i < GameSettings.INITIAL_GRASS_COUNT; i++) {
            new GrassSpawnAction().execute(map);
        }

        for (int i = 0; i < GameSettings.INITIAL_ROCK_COUNT; i++) {
            new RockSpawnAction().execute(map);
        }

        for (int i = 0; i < GameSettings.INITIAL_TREE_COUNT; i++) {
            new TreeSpawnAction().execute(map);
        }
    }
}
