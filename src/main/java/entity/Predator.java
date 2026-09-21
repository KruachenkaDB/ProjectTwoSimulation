package entity;

import map.Coordinates;
import map.Map;

public class Predator extends Creature {
    private final int powerAttack;

    public Predator(Coordinates coordinates, int speed, int health, int powerAttack, int hunger) {
        super(coordinates, speed, health, hunger);
        this.powerAttack = powerAttack;
    }

    @Override
    protected void interactWithTarget(Map map, Entity target) {
        if (target instanceof Herbivore) {
            Herbivore herbivore = (Herbivore) target;

            herbivore.takeDamage(powerAttack);

            if (herbivore.getHealth() <= 0) {
                restoreHunger();
                map.removeEntity(herbivore.getCoordinates());
            }
        }
    }

    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);

        return super.isSquareAvailableForMove(coordinates, map) || entity instanceof Herbivore;
    }
}
