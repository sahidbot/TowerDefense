package game;

import common.JavaFXThreadingRule;
import common.core.Vector2;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Monster on 2/14/2016.
 */
public class SideBarTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    @Before
    public void BeforeTestingSetup()
    {
        sidebar = new SideBar(sideBarWidth, sideBarHeight, leftOffset,topOffset, marginOrSeparation, towerWidth, inspectionHeight, shopTitleHeight);
    }
    private static final double sideBarHeight = 200;
    private static final double sideBarWidth = 100;
    private static final double towerWidth = 5;
    private static final double leftOffset = 10;
    private static final double topOffset = 15;
    private static final double marginOrSeparation = 1;
    private static final double inspectionHeight = 50;
    private static final double shopTitleHeight = 20;
    private static SideBar sidebar;
    @Test
    public void generateBuyableTowerPositionTests()
    {
        Vector2 firstPostion = sidebar.generateBuyableTowerPosition(1);
        // it should be at leftoffset (10) + marginOrSeparation (1) = 111
        assertEquals(11, firstPostion.getX(), 0.001);
        //it should be at margin (1) + topOffset (15) + margin (1) + shopTitleHeight (20) + margin (1) = 38
        assertEquals(38, firstPostion.getY(), 0.001);

        Vector2 secondPosition = sidebar.generateBuyableTowerPosition(2);
        // it should be at leftoffset (10) + margin (1) + tileOffset (5) + margin (1)
        assertEquals(17, secondPosition.getX(), 0.001);
        //it should be at margin (1) + topOffset (15) + margin (1) + shopTitleHeight (20) + margin (1) = 38
        assertEquals(38, secondPosition.getY(), 0.001);
    }
}