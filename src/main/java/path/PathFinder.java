package path;

import entity.Entity;
import entity.Grass;
import entity.Herbivore;
import entity.Predator;
import map.Coordinates;
import map.Map;
import map.TargetType;

import java.util.*;

public class PathFinder {
    public static LinkedList<Coordinates> findPathBfs(
            Entity entity,
            Map map,
            TargetType targetType
    ) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        java.util.Map<Coordinates, Coordinates> parent = new HashMap<>();
        LinkedList<Coordinates> path = new LinkedList<>();

        queue.add(entity.getCoordinates());
        visited.add(entity.getCoordinates());

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            Set<Coordinates> neighbours = entity.getNeighbours(current);

            for (Coordinates newCoordinates : neighbours) {

                if (visited.contains(newCoordinates)) {
                    continue;
                }

                Entity target = map.getEntity(newCoordinates);

                visited.add(newCoordinates);
                parent.put(newCoordinates, current);

                if (isTarget(entity, target, targetType)) {
                    Coordinates step = newCoordinates;

                    while (!step.equals(entity.getCoordinates())) {
                        path.addFirst(step);
                        step = parent.get(step);
                    }

                    return path;
                }

                if (map.isSquareEmpty(newCoordinates)) {
                    queue.add(newCoordinates);
                }
            }
        }

        return path;
    }

    static boolean isTarget(Entity entity, Entity target, TargetType targetType) {
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
}
