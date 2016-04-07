package test.game;

import common.core.Vector2;
import game.Critter;
import game.CritterHealthBar;
import game.CritterType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;
import javafx.scene.paint.Color;
import static org.junit.Assert.*;
/**
 * Created by saddamtahir on 2016-04-05.
 */
public class CritterHealthBarTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    CritterHealthBar bar;
    Critter leCritter;

    @Before
    public void BeforeTesting(){
        leCritter = new Critter(new Vector2(), CritterType.GROUND);
      bar = new CritterHealthBar(new Vector2(3, 3),100, leCritter);
    }

    @Test
    public void getBlendedColorHalfHealthTest(){
        Color actual = bar.getBlendedColor(0.5);
        Color expected = new Color(1.0,1.0,0.0,1.0);
        assertEquals(actual,expected);
    }

    @Test
    public void getBlendedColorFullHealthTest(){
        Color actual = bar.getBlendedColor(1);
        Color expected = new Color(0.0,1.0,0.0,1.0);
        assertEquals(actual,expected);
    }

    @Test
    public void getBlendedColorLowHealthTest(){
        Color actual = bar.getBlendedColor(0);
        Color expected = new Color(1.0,0.0,0.0,1.0);
        assertEquals(actual,expected);
    }
}
