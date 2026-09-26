import config.GameSettings;
import map.GameMap;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        GameMap gameMap = new GameMap(GameSettings.MAP_WIDTH, GameSettings.MAP_HEIGHT);

        SimulationController controller = new SimulationController(gameMap);

        SwingUtilities.invokeLater(controller::start);
    }
}
