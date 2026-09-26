package map;

import java.util.Objects;

public class Coordinates {
    private final int y;
    private final int x;

    public Coordinates(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Coordinates shift(Coordinates shift) {
        return new Coordinates(
                this.y + shift.getY(),
                this.x + shift.getX()
        );
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Coordinates other = (Coordinates) object;

        return Objects.equals(y, other.y) && Objects.equals(x, other.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}
