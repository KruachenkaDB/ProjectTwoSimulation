package spawn;

import entity.*;
import map.Coordinates;
import map.GameMap;

import java.util.Random;

public abstract class EntitySpawner<T extends Entity> {
    private final Random random = new Random();

    protected abstract T createEntity(Coordinates coordinates);

    public void spawn(GameMap gameMap) {
            Coordinates randomCoordinates = getRandomFreeCoordinates(gameMap);
            gameMap.putEntity(randomCoordinates, createEntity(randomCoordinates));
    }

    private Coordinates getRandomFreeCoordinates(GameMap gameMap) {
        Coordinates randomCoordinates = new Coordinates(
                random.nextInt(gameMap.getHeight()),
                random.nextInt(gameMap.getWidth())
        );

        while (!gameMap.isSquareEmpty(randomCoordinates)) {
            randomCoordinates =
                    new Coordinates(random.nextInt(gameMap.getHeight()),
                            random.nextInt(gameMap.getWidth()));
        }

        return randomCoordinates;
    }
}