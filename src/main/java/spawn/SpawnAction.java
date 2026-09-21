package spawn;

import entity.*;
import map.Coordinates;
import map.Map;

public abstract class SpawnAction<T extends Entity> {
    protected abstract T createEntity(Coordinates coordinates);
    public void execute(Map map) {
            Coordinates randomCoordinates = map.getRandomCoordinates();
            map.setEntitys(randomCoordinates, createEntity(randomCoordinates));
    }
}