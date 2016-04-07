package test.game;

import game.InspectionPanel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;
import static org.junit.Assert.*;

/**
 * Created by saddamtahir on 2016-04-06.
 */
public class InspectionPanelTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    InspectionPanel inspectionpanel;

    @Before
    public void BeforeTestingSetup()
    {
    inspectionpanel = new InspectionPanel(4.0,4.0,1.0,1.0);
    }

    @Test
    public void getCurrentYPositionTest()
    {
        double result = inspectionpanel.getCurrentYPosition(2,2,2);
        assertEquals(8,result,0.001);
    }
}
