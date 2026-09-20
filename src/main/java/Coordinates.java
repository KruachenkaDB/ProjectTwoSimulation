import java.util.Objects;

public class Coordinates {

//    почему указали не инт а интеджер
    public final Integer vertical;
    public final Integer gorizontal;

    public Coordinates(Integer vertical, Integer gorizontal) {
        this.vertical = vertical;
        this.gorizontal = gorizontal;
    }

    public Coordinates shift(CoordinatesShift shift) {
        return new Coordinates(this.vertical + shift.verticalShift, this.gorizontal + shift.gorizontalShift);
    }

    public boolean canShift(CoordinatesShift shift) {
        int v = vertical + shift.verticalShift;
        int g = gorizontal + shift.gorizontalShift;

        if ((v < 0) || (v >= GameSettings.MAP_HEIGHT)) return false;
        if ((g < 0) || (g >= GameSettings.MAP_WIDTH)) return false;

        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Coordinates that = (Coordinates) object;
        return Objects.equals(vertical, that.vertical) && Objects.equals(gorizontal, that.gorizontal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertical, gorizontal);
    }
}
