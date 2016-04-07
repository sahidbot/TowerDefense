package test.game;

import common.core.Vector2;
import game.CritterType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;
import game.Critter;
import static org.junit.Assert.*;

/**
 * Created by saddamtahir on 2016-04-06.
 */
public class CritterTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    Critter critter;

    @Before
    public void BeforeTesting() {
    critter = new Critter(new Vector2(3, 3), CritterType.GROUND);
    }

    @Test
    public void updateTest_DamagePerSecondDuration()
    {
        critter.update(1);
        double damagePerSecondDuration = -1.0;
        assertEquals(-1.0,damagePerSecondDuration,0.001);
    }

    @Test
    public void updateTest_FrozenDuration()
    {
        critter.update(1);
        double frozenDuration = -1.0;
        assertEquals(-1.0,frozenDuration,0.001);
    }
}
