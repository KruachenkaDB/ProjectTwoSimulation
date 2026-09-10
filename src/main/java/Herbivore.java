import java.awt.*;

import java.util.Set;

public class Herbivore extends Creature {
//Травоядное, наследуется от main.java.Creature.
// Стремятся найти ресурс (траву),
// может потратить свой ход на движение
// в сторону травы, либо на её поглощение.


    public Herbivore(Coordinates coordinates, int speed, Color color, int health_HP) {
        super(coordinates, speed, color, health_HP);
    }

    //здесь добавить метод по поиску ресурса

    @Override
    void makeMove() {
        super.makeMove();
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
