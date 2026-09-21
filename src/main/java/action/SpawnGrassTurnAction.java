package action;

import map.Map;
import spawn.GrassSpawnAction;

public class SpawnGrassTurnAction implements TurnAction {

    private final GrassSpawnAction grassSpawnAction = new GrassSpawnAction();

    @Override
    public void execute(Map map, int turnCounter) {
        grassSpawnAction.execute(map);
    }
}