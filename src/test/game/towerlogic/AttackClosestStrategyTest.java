package test.game.towerlogic;

import common.core.Vector2;
import game.Critter;
import game.CritterType;
import game.towerlogic.AttackClosestStrategy;
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
public class AttackClosestStrategyTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    private ArrayList<Critter> crittersorder1;
    private ArrayList<Critter> crittersorder2;
    private Critter zeroZeroCritter;
    private Critter fourFourCritter;
    private Tower tower;
    AttackClosestStrategy strategy;
    private ArrayList<Critter> zeroZeroCritterList;

    @Before
    public void BeforeTestingSetup(){

        zeroZeroCritter = new Critter(16,16,new Vector2(0,0), CritterType.GROUND);
        fourFourCritter = new Critter(16,16, new Vector2(4,4), CritterType.AIR);

        zeroZeroCritterList = new ArrayList<>();
        zeroZeroCritterList.add(zeroZeroCritter);

        crittersorder1 = new ArrayList<>();
        crittersorder1.add(zeroZeroCritter);
        crittersorder1.add(fourFourCritter);

        crittersorder2 = new ArrayList<>();
        crittersorder2.add(fourFourCritter);
        crittersorder2.add(zeroZeroCritter);

        tower = new Tower(TowerType.ARROW, new Vector2(3,3));
        strategy = new AttackClosestStrategy(tower);
    }

    @Test
    public void doDamageSelectsProperTargetTest(){
        List<Critter> result = strategy.doDamage(crittersorder1);
        assertEquals(1, result.size());
        assertEquals(fourFourCritter, result.get(0));

        List<Critter> result2 = strategy.doDamage(crittersorder2);
        assertEquals(1, result2.size());
        assertEquals(fourFourCritter, result.get(0));

    }

    @Test
    public void doDamageDoesDamageTest(){
        zeroZeroCritter.setHealthPoints(100);
        tower.setDamage(30);

        List<Critter> result = strategy.doDamage(zeroZeroCritterList);
        assertEquals(65, result.get(0).getHealthPoints(), 0.001);
    }

    @Test
    public void doDamageDoesDamageToOnlyOneEnemyTest(){
        zeroZeroCritter.setHealthPoints(100);
        fourFourCritter.setHealthPoints(100);
        tower.setDamage(30);

        List<Critter> result = strategy.doDamage(crittersorder1);
        assertEquals(100, zeroZeroCritter.getHealthPoints(), 0.001f);
        assertEquals(65, fourFourCritter.getHealthPoints(), 0.001f);


    }
}