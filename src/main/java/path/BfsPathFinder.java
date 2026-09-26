package path;

import entity.Entity;
import map.Coordinates;
import map.GameMap;

import java.util.*;

public final class BfsPathFinder {
    private BfsPathFinder() {
    }

    public static LinkedList<Coordinates> find(GameMap gameMap, Coordinates start, Class<? extends Entity> target) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            for (Coordinates neighbour : getNeighbours(current, gameMap)) {
                if (visited.contains(neighbour)) {
                    continue;
                }

                Entity entity = gameMap.getEntity(neighbour);

                visited.add(neighbour);
                parent.put(neighbour, current);

                if (isTarget(entity, target)) {
                    return buildPath(parent, start, neighbour);
                }

                if (gameMap.isSquareEmpty(neighbour)) {
                    queue.add(neighbour);
                }
            }
        }

        return new LinkedList<>();
    }

    private static boolean isTarget(Entity entity, Class<? extends Entity> target) {
        return (entity != null) && target.isInstance(entity);
    }

    private static LinkedList<Coordinates> buildPath(Map<Coordinates, Coordinates> parent, Coordinates start, Coordinates target) {
        LinkedList<Coordinates> path = new LinkedList<>();
        Coordinates current = target;

        while (!current.equals(start)) {
            path.addFirst(current);
            current = parent.get(current);
        }

        return path;
    }

    private static Set<Coordinates> getNeighbours(Coordinates current, GameMap gameMap) {
        Set<Coordinates> neighbours = new HashSet<>();

        for (Coordinates shift : getEntityMoves()) {
            Coordinates next = current.shift(shift);

            if (gameMap.isInside(next)) {
                neighbours.add(next);
            }
        }

        return neighbours;
    }

    private static Set<Coordinates> getEntityMoves() {
        return Set.of(
                new Coordinates(1, 0),
                new Coordinates(-1, 0),
                new Coordinates(0, 1),
                new Coordinates(0, -1)
        );
    }
}
