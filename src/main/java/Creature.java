import java.awt.*;
import java.util.LinkedList;
import java.util.Set;

abstract class Creature extends Entity {
    // Существо, имеет скорость
    // (сколько клеток может пройти
    // за 1 ход), количество HP.
    // Имеет метод makeMove() - сделать ход.

    public int speed;
    public int health_HP;
    int hunger;

    private static final int HUNGER_DECREASE = 10;
    private static final int STARVATION_DAMAGE = 5;
    private Map map;
    private Entity entity;

    public Creature(Coordinates coordinates, int speed, int health_HP, int hunger) {
        super(coordinates);
        this.speed = speed;
        this.health_HP = health_HP;
        this.hunger = hunger;
    }

    //перенести сюда общий код
    void makeMove(Map map) {
    }

    public int getHealth_HP() {
        return health_HP;
    }

    void decreaseHunger() {
        hunger -= HUNGER_DECREASE;

        if (hunger < 0) {
            hunger = 0;
        }

        if (hunger == 0) {
            takeDamage(STARVATION_DAMAGE);
        }
    }

    void takeDamage(int damage) {
        if (hunger == 0) {
            health_HP -= damage;
        }
    }

    //тут переменную макс хангур подсатвить
    void restoreHunger() {
        hunger = 100;
    }

    void reproduce(Map map, Creature partner) {
        Set<Coordinates> availableMoves = getAvailableMoveSquares(coordinates, map);

        if (!availableMoves.isEmpty() && (partner.hunger > 10) && (this.hunger > 10)) {
            Coordinates coordinate = availableMoves.iterator().next();
            map.setEntitys(coordinate, createOffSpring(coordinate));
        }
    }

    //ереименовать метод на криэйт спрайт
    Creature createOffSpring(Coordinates coordinate) {
        if (this instanceof Predator) {
            return new Predator(coordinate, Map.PREDATOR_SPEED, Map.PREDATOR_HEALTH, Map.PREDATOR_ATTACK, Map.MAX_HUNGER);
        } else if (this instanceof Herbivore) {
            return new Herbivore(coordinate, Map.HERBIVORE_SPEED, Map.HERBIVORE_HEALTH, Map.MAX_HUNGER);
        }
        return null;
    }

    Creature findPartner(Map map) {
        LinkedList<Coordinates> path = getPathAlgoritmBfs(this, map, TargetType.PARTNER);

        if (!path.isEmpty()) {
            Coordinates lastCoordinates = path.getLast();

            Entity entity = map.getEntity(lastCoordinates);
            if (entity instanceof Creature) {
                return (Creature) entity;
            }
        }
        return null;
    }

    boolean isPartnerNearby(Creature partner) {
        for (CoordinatesShift shift : getEntityMoves()) {
            if (coordinates.canShift(shift)) {
                Coordinates newCoordinates = coordinates.shift(shift);

                if (newCoordinates.equals(partner.coordinates)) {
                    return true;
                }
            }
        }
        return false;
    }
}
