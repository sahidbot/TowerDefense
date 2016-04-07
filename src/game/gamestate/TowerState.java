package game.gamestate;

import game.towerlogic.AttackStrategyEnum;
import game.towerlogic.TowerType;

import java.util.UUID;

/**
 * State of the tower
 */
public class TowerState {
    public String id;
    public int posX;
    public int posY;
    public TowerType towerType;
    public AttackStrategyEnum strategy;
    public int level;
}
