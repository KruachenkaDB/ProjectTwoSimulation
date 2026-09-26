package spawn;

import entity.Rock;
import map.Coordinates;

public class RockSpawner extends EntitySpawner<Rock> {
    @Override
    protected Rock createEntity(Coordinates coordinates) {
        return new Rock();
    }
}