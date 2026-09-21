package spawn;

import entity.Rock;
import map.Coordinates;

public class RockSpawnAction extends SpawnAction<Rock> {
    @Override
    protected Rock createEntity(Coordinates coordinates) {
        return new Rock(coordinates);
    }
}