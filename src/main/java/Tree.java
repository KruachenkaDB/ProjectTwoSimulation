import java.util.Set;

public class Tree extends Entity{
    public Tree(Coordinates coordinates) {
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
