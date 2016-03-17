package test.common;

import common.TileManager;
import common.core.Vector2;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

/**
 * Created by Monster on 3/11/2016.
 */
public class TileManagerTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private TileManager leTileManager;

    @Before
    public void BeforeTestingSetup(){

        leTileManager = new TileManager();
        leTileManager.createScenery(10,10);
    }

    @Test
    public void getTilePositionTest()
    {
        Vector2 testPosition = new Vector2(5.0,5.0);
        Vector2 returnVector2 = leTileManager.getTilePosition(testPosition);
        Assert.assertEquals(returnVector2.getX(),1.0);
        Assert.assertEquals(returnVector2.getY(),1.0);
    }

}