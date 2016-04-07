package game.gamestate;

import common.core.Vector2;
import game.towerlogic.Tower;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * State of the game
 */
public class GameState implements Serializable {
    public List<TowerState> towers = new ArrayList<>();
    public int level;
    public int availableGold;
}
