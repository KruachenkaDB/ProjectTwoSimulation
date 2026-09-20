package map;

import config.GameSettings;

import java.util.Objects;

public class Coordinates {
    public final Integer vertical;
    public final Integer horizontal;

    public Coordinates(Integer vertical, Integer horizontal) {
        this.vertical = vertical;
        this.horizontal = horizontal;
    }

    public Coordinates shift(CoordinatesShift shift) {
        return new Coordinates(
                this.vertical + shift.verticalShift,
                this.horizontal + shift.horizontalShift
        );
    }

    public boolean canShift(CoordinatesShift shift) {
        int v = vertical + shift.verticalShift;
        int h = horizontal + shift.horizontalShift;

        if ((v < 0) || (v >= GameSettings.MAP_HEIGHT)) return false;
        if ((h < 0) || (h >= GameSettings.MAP_WIDTH)) return false;

        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Coordinates that = (Coordinates) object;

        return Objects.equals(vertical, that.vertical) && Objects.equals(horizontal, that.horizontal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertical, horizontal);
    }
}
