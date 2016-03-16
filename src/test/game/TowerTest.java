package test.game;

import common.core.Vector2;
import game.SideBar;
import game.towerlogic.Tower;
import game.towerlogic.TowerType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

import static org.junit.Assert.*;

/**
 * Created by Monster on 3/16/2016.
 */
public class TowerTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private Tower leTower;

    @Before
    public void BeforeTestingSetup() {
        leTower = new Tower(TowerType.ARROW, new Vector2());
    }


    @Test
    public void isTimeToFireTest(){
        double rof = leTower.getRateOfFire();
        assertFalse(leTower.isTimeToFire(0));
        assertTrue(leTower.isTimeToFire(rof));
        assertFalse(leTower.isTimeToFire(0));
    }
}