package action;

import map.Map;

public interface TurnAction {
    void execute(Map map, int turnCounter);
}
