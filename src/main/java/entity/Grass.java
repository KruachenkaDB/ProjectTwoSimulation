package entity;

import map.Coordinates;
import map.CoordinatesShift;

import java.util.Set;

public class Grass extends Entity {
    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Set<CoordinatesShift> getEntityMoves() {
        return Set.of();
    }
}
