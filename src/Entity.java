import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class Entity {
    // Корневой абстрактный класс для всех
    // существ и объектов существующих в симуляции.

    //тут может в отдельные классы запистаь последовательсность в будущем
    // сделать перечисление

    // сделаем что травоядное ходит по диагонали со скоростью 1 клетка за ход
    // хищник 2 клетки за ход, но не может ходить по диагонали
    public Coordinates coordinates;

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    // Доступные ячейки для хода
    public Set<Coordinates> getAvailableMoveSquares(Map map) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift: getEntityMoves()) {
            if (coordinates.canShift(shift)) {
                Coordinates newCoordinates = coordinates.shift(shift);

                if(isSquareAvailableForMove(newCoordinates, map)) {
                    result.add(newCoordinates);
                }
            }
        }

        return result;
    }

    //этот метод переопределить для хищника
    // Доступные ячейки для хода
    protected boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);
        return map.isSquareEmpty(coordinates) || entity instanceof Grass;
    }

//    //тут пропишем алгоритм
//    public void {
//
//    }

    protected Set<CoordinatesShift> getEntityMoves() {
        return new HashSet<>(Arrays.asList(
                new CoordinatesShift(1, 0),
                new CoordinatesShift(-1, 0),
                new CoordinatesShift(0, 1),
                new CoordinatesShift(0, -1)
        ));
    }
}


