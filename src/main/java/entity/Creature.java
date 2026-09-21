package entity;

import config.GameSettings;
import map.Coordinates;
import map.Map;
import map.TargetType;
import path.PathFinder;

import java.util.LinkedList;

public abstract class Creature extends Entity {
    private final int speed;
    private int health;
    private int hunger;

    Creature(Coordinates coordinates, int speed, int health, int hunger) {
        super(coordinates);
        this.speed = speed;
        this.health = health;
        this.hunger = hunger;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public void makeMove(Map map) {
        LinkedList<Coordinates> path =
                PathFinder.findPathBfs(this, map, TargetType.FOOD);

        int steps = 0;

        while (steps < getSpeed()) {
            if (path.isEmpty()) {
                break;
            }

            Coordinates nextCoordinates = path.removeFirst();
            Entity target = map.getEntity(nextCoordinates);

            if (target != null) {
                interactWithTarget(map, target);
                break;
            }

            map.moveEntity(getCoordinates(), nextCoordinates);
            steps++;
        }
    }

    protected abstract void interactWithTarget(Map map, Entity target);

    public void decreaseHunger() {
        hunger -= GameSettings.HUNGER_DECREASE;

        if (hunger < 0) {
            hunger = 0;
        }

        if (hunger == 0) {
            takeDamage(GameSettings.HUNGER_DAMAGE);
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void restoreHunger() {
        hunger = GameSettings.MAX_HUNGER;
    }
}
