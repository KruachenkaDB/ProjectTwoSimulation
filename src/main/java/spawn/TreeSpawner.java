package spawn;

import entity.Tree;
import map.Coordinates;

public class TreeSpawner extends EntitySpawner<Tree> {
    @Override
    protected Tree createEntity(Coordinates coordinates) {
        return new Tree();
    }
}