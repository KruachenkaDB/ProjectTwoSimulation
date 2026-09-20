import java.lang.annotation.Target;
import java.util.*;

public abstract class Entity {
    public Coordinates coordinates;
    private Map map;
//    TargetType targetType;
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

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

    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        Entity entity = map.getEntity(coordinates);
        return map.isSquareEmpty(coordinates) || entity instanceof Grass;
    }

    // переделать название метода
    public LinkedList<Coordinates> getPathAlgoritmBfs(Entity entity, Map map, TargetType targetType) {
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
                // тут была цель поиска перенесла в отдельный метод
                if (!visited.contains(newCoordinates)) {
                    visited.add(newCoordinates);
                    parent.put(newCoordinates, current);
                    queue.add(newCoordinates);

                    Entity target = map.getEntity(newCoordinates);
                    Coordinates step = newCoordinates;

                    if (isTarget(entity, target, targetType)) {
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

    boolean isTarget (Entity entity, Entity target, TargetType targetType) {
        System.out.println(entity + " -> " + target);
        if (target == null) {
            return false;
        }
        if (targetType == TargetType.FOOD) {
            return ((entity instanceof Predator) && (target instanceof Herbivore))
                    || ((entity instanceof Herbivore) && (target instanceof Grass));

        } else if (targetType == TargetType.PARTNER) {
            if (target != entity) {
                return ((entity instanceof Predator) && (target instanceof Predator))
                        || ((entity instanceof Herbivore) && (target instanceof Herbivore));
            }
        }

        return false;
    }

    public Set<CoordinatesShift> getEntityMoves() {
        return new HashSet<>(Arrays.asList(
                new CoordinatesShift(1, 0),
                new CoordinatesShift(-1, 0),
                new CoordinatesShift(0, 1),
                new CoordinatesShift(0, -1)
        ));
    }

    //поменять имя переменной
//    boolean isPartner(Entity entity, Coordinates availableMoves) {
//        Entity target = map.getEntity(availableMoves);
//        boolean isPartner = ((entity instanceof Predator) && (target instanceof Predator))
//                || ((entity instanceof Herbivore) && (target instanceof Herbivore));
//        return  isPartner;
//    }
//
}


