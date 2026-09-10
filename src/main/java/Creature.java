import java.awt.*;

abstract class Creature extends Entity{
    //Абстрактный класс,
    // наследуется от main.java.Entity.
    // Существо, имеет скорость
    // (сколько клеток может пройти
    // за 1 ход), количество HP.
    // Имеет метод makeMove() - сделать ход.

    public int speed;
    //может здоровье отдельно добавить мышонку только?
    public int health_HP;
    public Color color;

    public Creature(Coordinates coordinates, int speed, Color color, int health_HP) {
        super(coordinates);
        this.speed = speed;
        this.color = color;
        this.health_HP = health_HP;
    }

    void makeMove(){
    }
}
