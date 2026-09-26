package entity;

import map.Coordinates;
import map.GameMap;
import path.BfsPathFinder;

import java.util.LinkedList;

public abstract class Creature extends Entity {
    private final int speed;
    private int health;
    private int hunger;

    private final int maxHunger;
    private final int hungerDecrease;
    private final int hungerDamage;

    private final Class<? extends Entity> target;

    private Coordinates coordinates;

    Creature(Coordinates coordinates, int speed, int health, int maxHunger, int hungerDecrease, int hungerDamage, Class<? extends Entity> target) {
        this.speed = speed;
        this.health = health;
        this.maxHunger = maxHunger;
        this.hunger = maxHunger;
        this.hungerDecrease = hungerDecrease;
        this.hungerDamage = hungerDamage;
        this.target = target;
        this.coordinates = coordinates;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void makeMove(GameMap gameMap) {
        LinkedList<Coordinates> path =
                BfsPathFinder.find(gameMap, getCoordinates(), target);

        int steps = 0;

        while ((steps < getSpeed()) && !path.isEmpty()) {
            Coordinates nextCoordinates = path.removeFirst();

            steps++;

            Entity targetEntity  = gameMap.getEntity(nextCoordinates);

            if (targetEntity  != null) {
                interactWithTarget(gameMap, targetEntity, nextCoordinates);
                break;
            }

            move(gameMap, nextCoordinates);
        }
    }

    protected abstract void interactWithTarget(GameMap gameMap, Entity target, Coordinates targetCoordinates);

    public void decreaseHunger() {
        hunger -= hungerDecrease;

        if (hunger < 0) {
            hunger = 0;
        }

        if (hunger == 0) {
            takeDamage(hungerDamage);
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void restoreHunger() {
        hunger = maxHunger;
    }

    public void move(GameMap gameMap, Coordinates to) {
        gameMap.removeEntity(getCoordinates());
        gameMap.putEntity(to, this);
    }
}
