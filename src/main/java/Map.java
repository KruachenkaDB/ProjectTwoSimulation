
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {

    //в будущем подумать о том чтобы можно было в симуляции проверить
    //точнее можно будет экспериментировать с количсевтом
    static final int PREDATOR_HEALTH = 100;
    static final int PREDATOR_ATTACK = 10;
    static final int HERBIVORE_HEALTH = 100;
    static final int PREDATOR_SPEED = 2;
    static final int HERBIVORE_SPEED = 1;

    //ормально ли производить вычесления с переменной макс...
    // и не получится она файнл
    static final int MAX_HUNGER = 100;

    HashMap<Coordinates, Entity> entitys = new HashMap<>();

    Random random = new Random();
    Coordinates randomCoordinates;

    public void setEntitys(Coordinates coordinates, Entity entity) {
        entity.coordinates = coordinates;
        entitys.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        entitys.remove(coordinates);
    }

    //мтеод переноса фигуры на новую клетку
    // получатеся в алгоритме мы будем двигаться на одну клетку ближе к вершине
    // исспользуя этот метод для изменения координат (либо вообще без этого метода)
    public void moveEntity(Coordinates from, Coordinates to) {
        Entity entity = getEntity(from);
        removeEntity(from);
        setEntitys(to, entity);
    }


    Coordinates getRandomCoordinates() {
        randomCoordinates =
                new Coordinates(random.nextInt(GameSettings.MAP_HEIGHT), random.nextInt(GameSettings.MAP_WIDTH));

        while (!isSquareEmpty(randomCoordinates)) {
            randomCoordinates =
                    new Coordinates(random.nextInt(GameSettings.MAP_HEIGHT), random.nextInt(GameSettings.MAP_WIDTH));
        }

        return randomCoordinates;
    }

    //переименовать метод
    public void initActions() {
        for (int i = 0; i < GameSettings.INITIAL_ENTITY_COUNT; i++) {
            randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Predator(randomCoordinates, PREDATOR_SPEED, PREDATOR_HEALTH, PREDATOR_ATTACK, MAX_HUNGER));
            randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Herbivore(randomCoordinates, HERBIVORE_SPEED, HERBIVORE_HEALTH, MAX_HUNGER));
            randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Grass(randomCoordinates));
            randomCoordinates = getRandomCoordinates();
            setEntitys(randomCoordinates, new Rock(randomCoordinates));
            randomCoordinates = getRandomCoordinates();
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

}