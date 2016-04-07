package test.game.towerlogic;

import common.SpriteType;
import common.Tile;
import common.TileManager;
import common.core.Vector2;
import game.Critter;
import game.CritterType;
import game.GameManager;
import game.towerlogic.AttackNearestToEndPointStrategy;
import game.towerlogic.Tower;
import game.towerlogic.TowerType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Monster on 4/6/2016.
 */
public class AttackNearestToEndPointTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    private ArrayList<Critter> crittersorder1;
    private ArrayList<Critter> crittersorder2;
    private Critter zeroOneCritter;
    private Critter fourFourCritter;
    private Tower tower;
    AttackNearestToEndPointStrategy strategy;
    private ArrayList<Critter> zeroOneCritterList;
    private Tile leEndPointTile;

    @Before
    public void BeforeTestingSetup(){

        GameManager.getInstance().tileManager = new TileManager(1,1, new String[0]);

        zeroOneCritter = new Critter(16,16,new Vector2(0,0), CritterType.GROUND);
        fourFourCritter = new Critter(16,16, new Vector2(4,4), CritterType.AIR);

        zeroOneCritterList = new ArrayList<>();
        zeroOneCritterList.add(zeroOneCritter);

        crittersorder1 = new ArrayList<>();
        crittersorder1.add(zeroOneCritter);
        crittersorder1.add(fourFourCritter);

        crittersorder2 = new ArrayList<>();
        crittersorder2.add(fourFourCritter);
        crittersorder2.add(zeroOneCritter);
        leEndPointTile = new Tile(SpriteType.EXIT_POINT, 32, 32, new Vector2());

        tower = new Tower(TowerType.ARROW, new Vector2(3,3));
        strategy = new AttackNearestToEndPointStrategy(tower){
            @Override
            public Tile getEndPoint() {
                return leEndPointTile;
            }
        };
    }
    @Test
    public void doDamageTest() throws Exception {

        zeroOneCritter.setHealthPoints(100);
        fourFourCritter.setHealthPoints(90);
        List<Critter> targets = strategy.doDamage(crittersorder1);
        assertEquals(1, targets.size());
        assertEquals(zeroOneCritter, targets.get(0));

        zeroOneCritter.setHealthPoints(100);
        fourFourCritter.setHealthPoints(90);
        List<Critter> targets2 = strategy.doDamage(crittersorder2);
        assertEquals(1, targets2.size());
        assertEquals(zeroOneCritter, targets2.get(0));

    }

    @Test
    public void doDamageTest2(){

        zeroOneCritter.setHealthPoints(10);
        fourFourCritter.setHealthPoints(90);
        List<Critter> targets = strategy.doDamage(crittersorder1);
        assertEquals(1, targets.size());
        assertEquals(zeroOneCritter, targets.get(0));

        zeroOneCritter.setHealthPoints(10);
        fourFourCritter.setHealthPoints(90);
        List<Critter> targets2 = strategy.doDamage(crittersorder2);
        assertEquals(1, targets2.size());
        assertEquals(zeroOneCritter, targets2.get(0));
    }

    @Test
    public void doDamageDoesDamageTest() {
        zeroOneCritter.setHealthPoints(100);
        tower.setDamage(10);
        List<Critter> result = strategy.doDamage(crittersorder1);
        assertEquals(85, result.get(0).getHealthPoints(), 0.001);
    }
}

