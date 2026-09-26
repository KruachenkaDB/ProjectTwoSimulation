package entity;

import map.Coordinates;
import map.GameMap;

public class Predator extends Creature {
    private final int powerAttack;

    public Predator(Coordinates coordinates, int speed, int health, int powerAttack, int hunger, int hungerDecrease, int hungerDamage) {
        super(coordinates, speed, health, hunger, hungerDecrease, hungerDamage, Herbivore.class);
        this.powerAttack = powerAttack;
    }

    @Override
    protected void interactWithTarget(GameMap gameMap, Entity target, Coordinates targetCoordinates) {
        if (target instanceof Herbivore herbivore) {

            herbivore.takeDamage(powerAttack);

            if (herbivore.getHealth() <= 0) {
                restoreHunger();
                gameMap.removeEntity(targetCoordinates);
            }
        }
    }
}
