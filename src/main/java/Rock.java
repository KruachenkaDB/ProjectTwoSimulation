import java.util.Set;

public class Rock extends Entity{
    //сделать его енам?

    public Rock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public Set<Coordinates> getAvailableMoveSquares(Map map) {
        return super.getAvailableMoveSquares(map);
    }

    @Override
    public Set<CoordinatesShift> getEntityMoves() {
        return Set.of();
    }
}
