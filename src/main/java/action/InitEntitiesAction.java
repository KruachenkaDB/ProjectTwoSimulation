package action;

import config.GameSettings;
import map.GameMap;
import spawn.*;

public class InitEntitiesAction implements Action {
    @Override
    public void execute(GameMap gameMap) {
        if (!hasEnoughSpace(gameMap)) {
            throw new IllegalStateException("There is not enough space on the field for all the objects");
        }

        spawn(() -> new PredatorSpawner().spawn(gameMap),
                GameSettings.INITIAL_PREDATOR_COUNT);

        spawn(() -> new HerbivoreSpawner().spawn(gameMap),
                GameSettings.INITIAL_HERBIVORE_COUNT);

        spawn(() -> new GrassSpawner().spawn(gameMap),
                GameSettings.INITIAL_GRASS_COUNT);

        spawn(() -> new RockSpawner().spawn(gameMap),
                GameSettings.INITIAL_ROCK_COUNT);

        spawn(() -> new TreeSpawner().spawn(gameMap),
                GameSettings.INITIAL_TREE_COUNT);
    }

    private void spawn(Runnable entitySpawner, int count) {
        for (int i = 0; i < count; i++) {
            entitySpawner.run();
        }
    }

    private boolean hasEnoughSpace(GameMap gameMap) {
        int totalEntities =
                GameSettings.INITIAL_PREDATOR_COUNT
                        + GameSettings.INITIAL_HERBIVORE_COUNT
                        + GameSettings.INITIAL_GRASS_COUNT
                        + GameSettings.INITIAL_ROCK_COUNT
                        + GameSettings.INITIAL_TREE_COUNT;

        int mapSize = gameMap.getWidth() * gameMap.getHeight();

        return totalEntities <= mapSize;
    }
}
