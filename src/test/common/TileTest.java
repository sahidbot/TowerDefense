package test.common;

import common.SpriteType;
import common.Tile;
import common.core.Vector2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

/**
 * Created by saddamtahir on 2016-03-17.
 */
public class TileTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    private Tile leTile;

    @Before
    public void BeforeTestingSetup()
    {
        Vector2 testPosition = new Vector2(1.0,1.0);
        leTile = new Tile(SpriteType.SCENERY,2.0,2.0,testPosition);
    }

    @Test
    public void getImageOffsetTest()
    {
        Vector2 returnVector = leTile.getImageOffset();
        Assert.assertEquals(returnVector.getX(),0,0.001);
        Assert.assertEquals(returnVector.getY(),64,0.001);
    }
}

