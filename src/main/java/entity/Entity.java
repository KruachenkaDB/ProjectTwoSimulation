package entity;

import map.Coordinates;
import map.CoordinatesShift;
import map.Map;
import map.TargetType;

import java.util.*;

public abstract class Entity {
    private Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
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
        return map.isSquareEmpty(coordinates);
    }

    public LinkedList<Coordinates> findPathBfs(Entity entity, Map map, TargetType targetType) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        java.util.Map<Coordinates, Coordinates> parent = new HashMap<>();
        LinkedList<Coordinates> path = new LinkedList<>();

        queue.add(entity.getCoordinates());
        visited.add(entity.getCoordinates());

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            Set<Coordinates> availableMoves = entity.getAvailableMoveSquares(current, map);

            for (Coordinates newCoordinates : availableMoves) {
                if (!visited.contains(newCoordinates)) {
                    visited.add(newCoordinates);
                    parent.put(newCoordinates, current);
                    queue.add(newCoordinates);

                    Entity target = map.getEntity(newCoordinates);
                    Coordinates step = newCoordinates;

                    if (isTarget(entity, target, targetType)) {
                        while (!step.equals(entity.getCoordinates())) {
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

    boolean isTarget(Entity entity, Entity target, TargetType targetType) {
        if (target == null) {
            return false;
        }

        if (targetType == TargetType.FOOD) {
            return ((entity instanceof Predator) && (target instanceof Herbivore))
                    || ((entity instanceof Herbivore) && (target instanceof Grass));
        }
//        else if (targetType == map.TargetType.PARTNER) {}
//            тут должна была быть логика поиска партнера

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
}