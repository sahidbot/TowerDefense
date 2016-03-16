package game;

import common.core.Vector2;
import game.towerlogic.Tower;

import java.util.ArrayList;

/**
 * Manager class for the critters.
 */
public class CritterManager {
    private ArrayList<Critter> critters;
    private int rewards = 0;

    public CritterManager() {
    }

    public ArrayList<Critter> getShootableCritters(Tower tower) {
        ArrayList<Critter> results = new ArrayList<Critter>();



        return results;
    }

    public ArrayList<Critter> getCritterNeighbours(Tower tower, ArrayList<Critter> critters, Critter selCritter) {
        ArrayList<Critter> results = new ArrayList<Critter>();



        return results;
    }

    public int getRewards() {
        int temp = rewards;
        rewards = 0;

        return temp;
    }

    // end logic
    // damage per second
    // frozen duration
}
