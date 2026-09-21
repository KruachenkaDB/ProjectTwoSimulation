package spawn;

import config.GameSettings;
import entity.Predator;
import map.Coordinates;

public class PredatorSpawnAction extends SpawnAction<Predator> {
    @Override
    protected Predator createEntity(Coordinates coordinates) {
        return new Predator(coordinates, GameSettings.PREDATOR_SPEED, GameSettings.PREDATOR_HEALTH, GameSettings.PREDATOR_ATTACK, GameSettings.MAX_HUNGER);
    }
}