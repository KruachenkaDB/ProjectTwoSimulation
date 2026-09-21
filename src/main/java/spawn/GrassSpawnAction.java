package spawn;

import entity.Grass;
import map.Coordinates;

public class GrassSpawnAction extends SpawnAction<Grass> {
    @Override
    protected Grass createEntity(Coordinates coordinates) {
        return new Grass(coordinates);
    }
}
