import java.util.*;


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
    // нужен ли нам этот метод если у нас есть абсолютно такой же но с другими параметрами
    public Set<Coordinates> getAvailableMoveSquares(Map map) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : getEntityMoves()) {
            if (coordinates.canShift(shift)) {
                Coordinates newCoordinates = coordinates.shift(shift);
                if (isSquareAvailableForMove(newCoordinates, map)) {
                    result.add(newCoordinates);
                }
            }
        }

        return result;
    }

    public Set<Coordinates> getAvailableMoveSquares(Coordinates current, Map map) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : getEntityMoves()) {
            if (current.canShift(shift)) {
                Coordinates newCoordinates = current.shift(shift);

                if (isSquareAvailableForMove(newCoordinates, map)) {
                    result.add(newCoordinates);
                }
            }
        }

        return result;
    }

    //этот метод переопределить для хищника
    // Доступные ячейки для хода
    //тут надо проверить, реализацию метода перенесла в метод травоядного
    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);
        return map.isSquareEmpty(coordinates) || entity instanceof Grass;
    }

    //тут пропишем алгоритм
    public LinkedList<Coordinates> getPathAlgoritmBfs(Entity entity, Map map) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        java.util.Map<Coordinates, Coordinates> parent = new HashMap<>();
        LinkedList<Coordinates> path = new LinkedList<>();

        queue.add(entity.coordinates);
        visited.add(entity.coordinates);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            //Для текущей клетки current найди все доступные соседние клетки и положи их в availableMoves
            Set<Coordinates> availableMoves = entity.getAvailableMoveSquares(current, map);

            for (Coordinates newCoordinates : availableMoves) {

                Entity target = map.getEntity(newCoordinates);
                boolean isTarget = ((entity instanceof Predator) && (target instanceof Herbivore))
                        || ((entity instanceof Herbivore) && (target instanceof Grass));

                if (!visited.contains(newCoordinates)) {
                    visited.add(newCoordinates);
                    parent.put(newCoordinates, current);
                    queue.add(newCoordinates);

                    Coordinates step = newCoordinates;
                    if (isTarget) {
                        while (!step.equals(entity.coordinates)) {
                            path.addFirst(step);
                            step = parent.get(step);
                        }
                        return path;
                    }
                }
            }
        }

        return new LinkedList<>();
    }

    public Set<CoordinatesShift> getEntityMoves() {
        return new HashSet<>(Arrays.asList(
                new CoordinatesShift(1, 0),
                new CoordinatesShift(-1, 0),
                new CoordinatesShift(0, 1),
                new CoordinatesShift(0, -1)
        ));
    }
}


