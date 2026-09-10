import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {
    HashMap<Coordinates, Entity> entitys = new HashMap<>();
    Random random = new Random();
    int randomCoordinate;

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
        // временно добавила чтобы понять ошибку
        //System.out.println("FROM: " + from.vertical + " " + from.gorizontal);
        //System.out.println("ENTITY: " + getEntity(from));

        Entity entity = getEntity(from);
        removeEntity(from);
        setEntitys(to, entity);
    }

    //setupDefaultEntityPosition
    public void initActions() {
        for (int i = 0; i < 10; i++) {
            // тут подумать над скоростью
            // пока внесла только хищников и травоядных, рандомно раскидала
            // они расставляясь рандомно могут поставиться дргу на друга, точнее зааменит того кто встал первым
            randomCoordinate = random.nextInt(100);
            setEntitys((new Coordinates(i, randomCoordinate)), new Predator(new Coordinates(i, randomCoordinate), 1, Color.RED, 100, 20));
            randomCoordinate = random.nextInt(100);
            setEntitys((new Coordinates(i, randomCoordinate)), new Herbivore(new Coordinates(i, randomCoordinate), 1, Color.YELLOW, 100));
            randomCoordinate = random.nextInt(100);
            setEntitys((new Coordinates(i, randomCoordinate)), new Grass(new Coordinates(i, randomCoordinate)));
            randomCoordinate = random.nextInt(100);
            setEntitys((new Coordinates(i, randomCoordinate)), new Rock(new Coordinates(i, randomCoordinate)));
            randomCoordinate = random.nextInt(100);
            setEntitys((new Coordinates(i, randomCoordinate)), new Tree(new Coordinates(i, randomCoordinate)));
        }
    }

    public boolean isSquareEmpty(Coordinates coordinates) {
        return !entitys.containsKey(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        return entitys.get(coordinates);
    }

    // временный метод чтобы проверить что мышь двигается на карте к еде, удалить/исправить!
    public List<Herbivore> getHerbivores() {
        List<Herbivore> herbivores = new ArrayList<>();

        for (Entity entity : entitys.values()) {
            if (entity instanceof Herbivore) {
                herbivores.add((Herbivore) entity);

            }
        }
        return herbivores;
    }
}