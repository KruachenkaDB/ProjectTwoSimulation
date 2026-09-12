import java.awt.*;

import java.util.LinkedList;
import java.util.Set;

public class Herbivore extends Creature {
    //Травоядное, наследуется от main.java.Creature.
//Стремятся найти ресурс (траву),
//может потратить свой ход на движение
//в сторону травы, либо на её поглощение.

    public Herbivore(Coordinates coordinates, int speed, int health_HP) {
        super(coordinates, speed, health_HP);
    }

    //здесь мне не нравятся методы, они пумтые, может что-то из энтити сюд перенести
    @Override
    void makeMove(Map map) {
        LinkedList<Coordinates> path = getPathAlgoritmBfs(this, map);

        if (!path.isEmpty()) {
            map.moveEntity(coordinates, path.getFirst());
            path.removeFirst();
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
