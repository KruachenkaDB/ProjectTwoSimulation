package spawn;

import entity.Tree;
import map.Coordinates;

public class TreeSpawnAction extends SpawnAction<Tree> {
    @Override
    protected Tree createEntity(Coordinates coordinates) {
        return new Tree(coordinates);
    }
}