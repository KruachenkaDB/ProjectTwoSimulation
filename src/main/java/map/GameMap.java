package map;

import entity.Creature;
import entity.Entity;
import java.util.*;

public class GameMap {
    private final Map<Coordinates, Entity> entities = new HashMap<>();

    private final int width;
    private final int height;

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void putEntity(Coordinates coordinates, Entity entity) {
        validate(coordinates);

        if (entity instanceof Creature creature) {
            creature.setCoordinates(coordinates);
        }

        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        validate(coordinates);
        entities.remove(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        validate(coordinates);
        return entities.get(coordinates);
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        validate(coordinates);
        return !entities.containsKey(coordinates);
    }

    public <T extends Entity> List<T> getEntitiesBy(Class<T> type) {
        List<T> result = new ArrayList<>();

        for (Entity entity : entities.values()) {
            if (type.isInstance(entity)) {
                result.add(type.cast(entity));
            }
        }

        return result;
    }

    public List<Entity> getAll() {
        return new ArrayList<>(entities.values());
    }

    public boolean isInside(Coordinates coordinates) {
        int y = coordinates.getY();
        int x = coordinates.getX();

       return y >= 0 && y < height && x >= 0 && x < width;
    }

    private void validate(Coordinates coordinates) {
        if (!isInside(coordinates)) {
            throw new IllegalArgumentException("Coordinates are outside the map: " + coordinates.getY() + ", " + coordinates.getX()
            );
        }
    }
}