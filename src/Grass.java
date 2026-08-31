import java.util.Set;

public class Grass extends Entity{
    public Grass(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Set<Coordinates> getAvailableMoveSquares(Map map) {
        return super.getAvailableMoveSquares(map);
    }

    @Override
    protected Set<CoordinatesShift> getEntityMoves() {
        return Set.of();
    }
}
