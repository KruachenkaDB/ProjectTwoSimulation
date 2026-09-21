package entity;

import map.Coordinates;
import map.Map;

public class Herbivore extends Creature {

    public Herbivore(Coordinates coordinates, int speed, int health, int hunger) {
        super(coordinates, speed, health, hunger);
    }

    @Override
    protected void interactWithTarget(Map map, Entity target) {
        if (target instanceof Grass) {
            restoreHunger();
            map.removeEntity(target.getCoordinates());
        }
    }

    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);

        return super.isSquareAvailableForMove(coordinates, map) || entity instanceof Grass;
    }
}