import java.awt.*;

import java.util.LinkedList;
import java.util.Set;

public class Herbivore extends Creature {
    //Травоядное, наследуется от main.java.Creature.
//Стремятся найти ресурс (траву),
//может потратить свой ход на движение
//в сторону травы, либо на её поглощение.

    private static final int FOOD_HUNGER_RESTORE = 100;

    public Herbivore(Coordinates coordinates, int speed, int health_HP, int hunger) {
        super(coordinates, speed, health_HP, hunger);
    }

    //здесь мне не нравятся методы, они пумтые, может что-то из энтити сюд перенести
    @Override
    void makeMove(Map map) {
        LinkedList<Coordinates> path = getPathAlgoritmBfs(this, map, TargetType.PARTNER);

        Creature partner = findPartner(map);

        if (partner != null) {
            System.out.println("Партнёр найден: " + partner.getCoordinates());
            System.out.println("Путь: " + path);
            if (isPartnerNearby(partner)) {
                reproduce(map, partner);
            } else {
                // идти к партнёру
                Coordinates nextCoordinates = path.getFirst();
                map.moveEntity(coordinates, nextCoordinates);
            }
        } else {
            path = getPathAlgoritmBfs(this, map, TargetType.FOOD);
            if (!path.isEmpty()) {
                Coordinates nextCoordinates = path.getFirst();
                Entity entity = map.getEntity(nextCoordinates);

                if (entity instanceof Grass) {
                    restoreHunger();
                }

                map.moveEntity(coordinates, nextCoordinates);
            }
        }
    }

    @Override
    public Set<Coordinates> getAvailableMoveSquares(Map map) {
        return super.getAvailableMoveSquares(map);
    }

    @Override
    public Set<CoordinatesShift> getEntityMoves() {
        return super.getEntityMoves();
    }
}