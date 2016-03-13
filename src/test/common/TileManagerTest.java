package test.common;

import common.TileManager;
import org.junit.Before;
import org.junit.Rule;
import test.JavaFXThreadingRule;

import static org.junit.Assert.*;

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
    }


}