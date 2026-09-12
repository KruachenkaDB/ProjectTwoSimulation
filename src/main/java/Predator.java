import java.awt.*;
import java.util.LinkedList;
import java.util.Set;

public class Predator extends Creature {
    // имеет силу атаки.
    // На что может потратить ход хищник:
    //Переместиться (чтобы приблизиться к жертве - травоядному)
    //Атаковать травоядное. При этом количество
    // HP травоядного уменьшается на силу атаки хищника.
    // Если значение HP жертвы опускается до 0,
    // травоядное исчезает

    int powerAttack;

    public Predator(Coordinates coordinates, int speed, Color color, int health_HP, int powerAttack) {
        super(coordinates, speed, color, health_HP);
        this.powerAttack = powerAttack;
    }

    @Override
    void makeMove(Map map) {
        super.makeMove(map);
    }

    @Override
    public Set<Coordinates> getAvailableMoveSquares(Map map) {
        return super.getAvailableMoveSquares(map);
    }

    //это наверное надо будет убрать посмотреть реализацию для пешкии думаю там похожая будет
    @Override
    public Set<CoordinatesShift> getEntityMoves() {
        return super.getEntityMoves();
    }

    @Override
    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);
        return map.isSquareEmpty(coordinates) || entity instanceof Herbivore;
    }
}
