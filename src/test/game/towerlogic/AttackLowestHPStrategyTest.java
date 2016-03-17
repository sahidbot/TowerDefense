package test.game.towerlogic;

import common.core.Vector2;
import game.Critter;
import game.CritterType;
import game.towerlogic.AttackLowestHPStrategy;
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
 * Created by Monster on 3/15/2016.
 */
public class AttackLowestHPStrategyTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    private ArrayList<Critter> crittersorder1;
    private ArrayList<Critter> crittersorder2;
    private Critter zeroZeroCritter;
    private Critter fourFourCritter;
    private Tower tower;
    AttackLowestHPStrategy strategy;
    private ArrayList<Critter> zeroZeroCritterList;

    @Before
    public void BeforeTestingSetup() {

        zeroZeroCritter = new Critter(16, 16, new Vector2(0, 0), CritterType.GROUND);
        fourFourCritter = new Critter(16, 16, new Vector2(4, 4), CritterType.AIR);

        zeroZeroCritterList = new ArrayList<>();
        zeroZeroCritterList.add(zeroZeroCritter);

        crittersorder1 = new ArrayList<>();
        crittersorder1.add(zeroZeroCritter);
        crittersorder1.add(fourFourCritter);

        crittersorder2 = new ArrayList<>();
        crittersorder2.add(fourFourCritter);
        crittersorder2.add(zeroZeroCritter);

        tower = new Tower(TowerType.ARROW, new Vector2(3, 3));
        strategy = new AttackLowestHPStrategy(tower);
    }

    @Test
    public void doDamageSelectsProperTargetTest() {
        fourFourCritter.setHealthPoints(10);
        zeroZeroCritter.setHealthPoints(100);
        List<Critter> result = strategy.doDamage(crittersorder1);
        assertEquals(1, result.size());
        assertEquals(fourFourCritter, result.get(0));

        fourFourCritter.setHealthPoints(100);
        zeroZeroCritter.setHealthPoints(10);
        List<Critter> result2 = strategy.doDamage(crittersorder2);
        assertEquals(1, result2.size());
        assertEquals(fourFourCritter, result.get(0));
    }

    @Test
    public void doDamageDoesDamageTest() {
        zeroZeroCritter.setHealthPoints(100);
        tower.setDamage(10);
        List<Critter> result = strategy.doDamage(zeroZeroCritterList);
        assertEquals(90, result.get(0).getHealthPoints(), 0.001);
    }
}