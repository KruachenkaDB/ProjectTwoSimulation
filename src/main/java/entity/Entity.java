package entity;

import map.Coordinates;
import map.CoordinatesShift;
import map.Map;
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

    public Set<Coordinates> getNeighbours(Coordinates current) {
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : getEntityMoves()) {
            if (current.canShift(shift)) {
                result.add(current.shift(shift));
            }
        }

        return result;
    }

    public boolean isSquareAvailableForMove(Coordinates coordinates, Map map) {
        return map.isSquareEmpty(coordinates);
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