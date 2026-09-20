package map;

import config.GameSettings;
import entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {
    private final java.util.Map<Coordinates, Entity> entitys = new HashMap<>();

    private final Random random = new Random();

    public void setEntitys(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entitys.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        entitys.remove(coordinates);
    }

    public void moveEntity(Coordinates from, Coordinates to) {
        Entity entity = getEntity(from);
        removeEntity(from);
        setEntitys(to, entity);
    }


    public Coordinates getRandomCoordinates() {
        Coordinates randomCoordinates = new Coordinates(random.nextInt(GameSettings.MAP_HEIGHT), random.nextInt(GameSettings.MAP_WIDTH));

        while (!isSquareEmpty(randomCoordinates)) {
            randomCoordinates =
                    new Coordinates(random.nextInt(GameSettings.MAP_HEIGHT),
                            random.nextInt(GameSettings.MAP_WIDTH));
        }

        return randomCoordinates;
    }

    public void initEntitys() {
        if (!hasEnoughSpace()) {
//            IllegalStateException - недопустимое состояние
            throw new IllegalStateException("На поле недостаточно места для всех объектов");
        }

        for (int i = 0; i < GameSettings.INITIAL_PREDATOR_COUNT; i++) {
            Coordinates randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Predator(randomCoordinates, GameSettings.PREDATOR_SPEED, GameSettings.PREDATOR_HEALTH, GameSettings.PREDATOR_ATTACK, GameSettings.MAX_HUNGER));
        }

        for (int i = 0; i < GameSettings.INITIAL_HERBIVORE_COUNT; i++) {
            Coordinates randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Herbivore(randomCoordinates, GameSettings.HERBIVORE_SPEED, GameSettings.HERBIVORE_HEALTH, GameSettings.MAX_HUNGER));
        }

        for (int i = 0; i < GameSettings.INITIAL_GRASS_COUNT; i++) {
            Coordinates randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Grass(randomCoordinates));
        }

        for (int i = 0; i < GameSettings.INITIAL_ROCK_COUNT; i++) {
            Coordinates randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Rock(randomCoordinates));
        }

        for (int i = 0; i < GameSettings.INITIAL_TREE_COUNT; i++) {
            Coordinates randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Tree(randomCoordinates));
        }
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !entitys.containsKey(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        return entitys.get(coordinates);
    }

    public List<Herbivore> getHerbivores() {
        List<Herbivore> herbivores = new ArrayList<>();

        for (Entity entity : entitys.values()) {
            if (entity instanceof Herbivore) {
                herbivores.add((Herbivore) entity);
            }
        }

        return herbivores;
    }

    public List<Predator> getPredators() {
        List<Predator> predators = new ArrayList<>();

        for (Entity entity : entitys.values()) {
            if (entity instanceof Predator) {
                predators.add((Predator) entity);
            }
        }

        return predators;
    }

    public List<Creature> getCreatures() {
        List<Creature> creatures = new ArrayList<>();

        for (Entity entity : entitys.values()) {
            if (entity instanceof Creature) {
                creatures.add((Creature) entity);
            }
        }

        return creatures;
    }

    private boolean hasEnoughSpace() {
        int totalEntities =
                GameSettings.INITIAL_PREDATOR_COUNT
                        + GameSettings.INITIAL_HERBIVORE_COUNT
                        + GameSettings.INITIAL_GRASS_COUNT
                        + GameSettings.INITIAL_ROCK_COUNT
                        + GameSettings.INITIAL_TREE_COUNT;

        int mapSize = GameSettings.MAP_WIDTH * GameSettings.MAP_HEIGHT;

        return totalEntities <= mapSize;
    }
}