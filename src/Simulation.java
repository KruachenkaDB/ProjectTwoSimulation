import java.awt.*;

public class Simulation {
    //Главный класс приложения, включает в себя:
    //
    public static final String ANSI_YELLOW_SQUARE_BACKGROUND = "\u001B[33m";
    public static final String ANSI_RED_SQUARE_BACKGROUND = "\u001B[31m";
    public static final String ANSI_GREEN_SQUARE_BACKGROUND = "\u001B[32m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001b[37m";

    //цбрала тут файнл
    private Map map;

    public Simulation() {
        this.map = map;
    }

    //Карту

    //Счётчик ходов

    //Рендерер поля
    public static void render(Map map) {
        for (int gorizontal = 0; gorizontal < 10; gorizontal++) {
            String line = "";
            for (int vertikal = 0; vertikal < 80; vertikal++) {
                Coordinates coordinates = new Coordinates(gorizontal, vertikal);
                if (map.isSquareEmpty(coordinates)){
                    line += getSpriteForEmptySquare(new Coordinates(vertikal, gorizontal));
                } else {
                    line += getEntitySprite(map.getEntity(coordinates));
                }
            }
            System.out.println(line);
        }
    }

    public static String getEntitySprite(Entity entity) {
        return colorizeSprite(selectUnicodeSpriteForEntity(entity), Color.YELLOW);
    }

    public static String selectUnicodeSpriteForEntity(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Predator":
                return "\uD83E\uDD89";
            case "Herbivore":
                return "\uD83D\uDC01";
            case "Grass":
                return "\uD83C\uDF3D";
            case "Rock":
                return "\uD83D\uDDFB";
            case "Tree":
                return "\uD83C\uDF32";
        }
        return "";
    }

    // в видео еще один параметр isSguareDark
    private static String colorizeSprite(String sprite, Color entityColor) {
        String result = sprite;

        if (entityColor == Color.YELLOW) {
            result = ANSI_YELLOW_SQUARE_BACKGROUND + result;
        } else if (entityColor == Color.RED) {
            result = ANSI_RED_SQUARE_BACKGROUND + result;
        } else if (entityColor == Color.GREEN) {
            result = ANSI_GREEN_SQUARE_BACKGROUND + result;
        } else result = ANSI_WHITE_SQUARE_BACKGROUND + result;

        return result;
    }

    private static String getSpriteForEmptySquare(Coordinates coordinates) {
        return colorizeSprite("=", Color.WHITE);
    }


    //просимулировать и отрендерить один ход
    void nextTurn(){

    }

    //запустить бесконечный цикл симуляции и рендеринга
    void startSimulation() {
        while (true) {
//он после каждого хода собирается рендерить доску
            // нам же скорее всего надо создать поток котрый будет
            // обновляться каждые пол секунды, или сделать так что один ход длится 2 секунды
            // из-за разной скорости хищники передвигаются быстрее, получается что за 1 ход
            // хищник пройдет 2 клетки, а травоядное 1 клетку

            render(map);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }


    //съедание происходит когда координаты совпадают (но не заюываем про пищевую цепочку
    // мышь не может съесть сову
    // приостановить бесконечный цикл симуляции и рендеринга
    void pauseSimulation(){

    }

    //Actions - список действий, исполняемых перед стартом симуляции или на каждом ходу (детали ниже)
    //Actions #
    //Action - действие, совершаемое над миром. Например - сходить всеми существами. Это действие итерировало бы существ и вызывало каждому makeMove(). Каждое действие описывается отдельным классом и совершает операции над картой. Симуляция содержит 2 массива действий:
    //
    //initActions - действия, совершаемые перед стартом симуляции. Пример - расставить объекты и существ на карте
    //turnActions - действия, совершаемые каждый ход. Примеры - передвижение существ, добавить травы или травоядных, если их осталось слишком мало
}
