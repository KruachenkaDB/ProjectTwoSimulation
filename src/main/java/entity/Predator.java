package entity;

import map.Coordinates;

import java.util.LinkedList;
import map.Map;
import map.TargetType;

public class Predator extends Creature {
    private final int powerAttack;

    public Predator(Coordinates coordinates, int speed, int health, int powerAttack, int hunger) {
        super(coordinates, speed, health, hunger);
        this.powerAttack = powerAttack;
    }

    @Override
    public void makeMove(Map map) {
        LinkedList<Coordinates> path = findPathBfs(this, map, TargetType.FOOD);

        int steps = 0;
        while (steps < getSpeed()) {
            if (path.isEmpty()) break;
            Entity entity = map.getEntity(path.getFirst());

            if (entity instanceof Herbivore) {
                Herbivore herbivore = (Herbivore) entity;

                herbivore.takeDamage(powerAttack);

                if (herbivore.getHealth() <= 0) {
                    restoreHunger();
                    map.removeEntity(herbivore.getCoordinates());
                }
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
                || entity instanceof Herbivore;
    }
}
