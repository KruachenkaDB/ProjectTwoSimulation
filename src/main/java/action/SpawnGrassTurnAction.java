package action;

import map.GameMap;
import spawn.GrassSpawner;

public class SpawnGrassTurnAction implements Action {

    private final GrassSpawner grassSpawner = new GrassSpawner();

    @Override
    public void execute(GameMap gameMap) {
        grassSpawner.spawn(gameMap);
    }
}