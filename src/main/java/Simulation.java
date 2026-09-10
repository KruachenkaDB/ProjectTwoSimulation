import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.Display;

public class Simulation {
    //Главный класс приложения, включает в себя:
    //
    public static final String ANSI_YELLOW_SQUARE_BACKGROUND = "\u001B[33m";
    public static final String ANSI_RED_SQUARE_BACKGROUND = "\u001B[31m";
    public static final String ANSI_GREEN_SQUARE_BACKGROUND = "\u001B[32m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001b[37m";
    private Terminal terminal;
    //цбрала тут файнл
    private Map map;

    public Simulation(Map map, Terminal terminal) {
        this.map = map;
        this.terminal = terminal;
    }

    //Карту

    //Счётчик ходов

    //Рендерер поля
    public void render(Map map) throws IOException {
        terminal.writer().print("\033[H");

        //разобраться с этой строкой
        StringBuilder board = new StringBuilder();

        for (int gorizontal = 0; gorizontal < 10; gorizontal++) {
            for (int vertikal = 0; vertikal < 80; vertikal++) {
                Coordinates coordinates = new Coordinates(gorizontal, vertikal);

                if (map.isSquareEmpty(coordinates)) {
                    board.append(getSpriteForEmptySquare(new Coordinates(vertikal, gorizontal)));
                } else {
                    board.append(getEntitySprite(map.getEntity(coordinates)));
                }
            }
            board.append('\n');
        }

        terminal.writer().print(board);
        terminal.writer().flush();
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
    void nextTurn(LinkedList<Coordinates> path, Entity entity) {
        map.moveEntity(entity.coordinates, path.getFirst());
        path.removeFirst();
    }

    //запустить бесконечный цикл симуляции и рендеринга
    //нужна ли обработка ошибки, произошли изменения в коде
    void startSimulation(LinkedList<Coordinates> path, Entity entity) throws IOException{
        while (!path.isEmpty()) {
            nextTurn(path, entity);

            render(map);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //рендерить доску после каждого хода? сейчас рендерится подряд друг за другом, надо чтобы доска обновлялась на одном месте, чтобы обновляла сама себя
            //Надо ли чтобы они передвигались с разной скоростью? Думаю нет...

        }
    }


    //съедание происходит когда координаты совпадают (но не заюываем про пищевую цепочку
    // мышь не может съесть сову
    // приостановить бесконечный цикл симуляции и рендеринга
    void pauseSimulation() {

    }

    //Actions - список действий, исполняемых перед стартом симуляции или на каждом ходу (детали ниже)
    //Actions #
    //Action - действие, совершаемое над миром. Например - сходить всеми существами.
    // Это действие итерировало бы существ и вызывало каждому makeMove().
    // Каждое действие описывается отдельным классом и совершает операции над картой.
    // Симуляция содержит 2 массива действий:
    //
    //initActions - действия, совершаемые перед стартом симуляции. Пример - расставить объекты и существ на карте
    //turnActions - действия, совершаемые каждый ход. Примеры - передвижение существ, добавить травы или травоядных, если их осталось слишком мало


}
