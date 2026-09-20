package entity;

import map.Coordinates;

import java.util.LinkedList;
import map.Map;
import map.TargetType;

public class Herbivore extends Creature {
    public Herbivore(Coordinates coordinates, int speed, int health, int hunger) {
        super(coordinates, speed, health, hunger);
    }

    @Override
    public void makeMove(Map map) {
        LinkedList<Coordinates> path = findPathBfs(this, map, TargetType.FOOD);

        int steps = 0;
        while (steps < getSpeed()) {
            if (path.isEmpty()) break;
            Entity entity = map.getEntity(path.getFirst());

            if (entity instanceof Grass) {
                Grass grass = (Grass) entity;
                restoreHunger();
                map.removeEntity(grass.getCoordinates());
                break;
            } else {
                map.moveEntity(getCoordinates(), path.getFirst());
                path.removeFirst();
            }
            steps++;
        }
    }

    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);

        return super.isSquareAvailableForMove(coordinates, map)
                || entity instanceof Grass;
    }
}