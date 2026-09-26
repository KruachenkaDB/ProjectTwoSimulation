package entity;

import map.Coordinates;
import map.GameMap;

public class Herbivore extends Creature {

    public Herbivore(Coordinates coordinates, int speed, int health, int hunger, int hungerDecrease, int hungerDamage) {
        super(coordinates, speed, health, hunger, hungerDecrease, hungerDamage, Grass.class);
    }

    @Override
    protected void interactWithTarget(GameMap gameMap, Entity target, Coordinates targetCoordinates) {
        if (target instanceof Grass) {
            restoreHunger();
            gameMap.removeEntity(targetCoordinates);
        }
    }
}