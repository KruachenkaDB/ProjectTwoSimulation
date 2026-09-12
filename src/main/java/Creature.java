import java.awt.*;
import java.util.LinkedList;

abstract class Creature extends Entity{
    // Существо, имеет скорость
    // (сколько клеток может пройти
    // за 1 ход), количество HP.
    // Имеет метод makeMove() - сделать ход.

    public int speed;
    //может здоровье отдельно добавить мышонку только?
    public int health_HP;

    public Creature(Coordinates coordinates, int speed, int health_HP) {
        super(coordinates);
        this.speed = speed;
        this.health_HP = health_HP;
    }

    void makeMove(Map map){
    }
}
