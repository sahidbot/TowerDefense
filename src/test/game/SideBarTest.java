package test.game;

import common.core.Vector2;
import game.SideBar;
import game.towerlogic.Tower;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test Class for SideBar
 * @version $revision $
 */
public class SideBarTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    @Before
    public void BeforeTestingSetup()
    {
        sidebar = new SideBar(sideBarWidth, sideBarHeight, leftOffset,topOffset, marginOrSeparation, towerWidth, inspectionHeight, shopTitleHeight);
    }
    private double sideBarHeight = 200;
    private double sideBarWidth = 100;
    private double towerWidth = 5;
    private double leftOffset = 10;
    private double topOffset = 15;
    private double marginOrSeparation = 1;
    private double inspectionHeight = 50;
    private double shopTitleHeight = 20;
    private SideBar sidebar;
    @Test
    public void generateBuyableTowerPosition_ExpectedPositionsTest()
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

    @Test
    public void sideBarConstructor_CreatesAvailableTowersNoDuplicatesTest() {
        Tower[] towersAvailable = sidebar.getTowersAvailable();
        Boolean duplicates = false;
        for (int j = 0; j < towersAvailable.length; j++)
            for (int k = j + 1; k < towersAvailable.length; k++)
                if (k != j && towersAvailable[k].getTowerType() == towersAvailable[j].getTowerType())
                    duplicates = true;
        assertFalse(duplicates);
    }

    @Test
    public void sideBarConstructor_AllTowersAvailableTest(){
        Tower[] towersAvailable = sidebar.getTowersAvailable();
        assertEquals(3,towersAvailable.length);
    }

}