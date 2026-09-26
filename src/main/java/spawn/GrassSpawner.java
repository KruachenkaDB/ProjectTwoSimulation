package spawn;

import entity.Grass;
import map.Coordinates;

public class GrassSpawner extends EntitySpawner<Grass> {
    @Override
    protected Grass createEntity(Coordinates coordinates) {
        return new Grass();
    }
}
