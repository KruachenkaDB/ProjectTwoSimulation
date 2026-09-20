package entity;

import config.GameSettings;
import map.Coordinates;
import map.Map;

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

    public abstract void makeMove(Map map);

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
