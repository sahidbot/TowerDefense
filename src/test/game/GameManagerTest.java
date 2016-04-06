package test.game;

import common.SpriteType;
import common.Tile;
import common.TileManager;
import common.core.*;
import game.GameManager;
import game.towerlogic.Tower;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import test.JavaFXThreadingRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static org.junit.Assert.*;

/**
 * Created by Monster on 3/2/2016.
 */
public class GameManagerTest {
    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    Canvas leCanvas;
    private double canvasHeight = 200;
    private double canvasWidth = 200;
    GameManager leGameManager;
    MouseHandler leMouseHandler;
    TileManager leTileManager;
    private Tile[][] tileManager_GetTilesOverlay;
    private MouseState mouseState_GetMouseState;
    private int tileManager_GetRows;
    private int tileManager_GetColumns;
    private int tileManager_GetWidth;
    private int tileManager_GetHeight;
    private List<Observer> mouseHandler_Observers;

    @Before
    public void BeforeTestingSetup() {

        Group root = new Group();
        Scene scene = new Scene(root);
        leCanvas = new Canvas(canvasWidth, canvasHeight);

        mouseState_GetMouseState = new MouseState();
        mouseHandler_Observers = new ArrayList<>();
        leMouseHandler = new MouseHandler(scene){
            @Override
            public MouseState getMouseState() {
                return mouseState_GetMouseState;
            }

            @Override
            public synchronized void addObserver(Observer o) {
                mouseHandler_Observers.add(o);
            }

            @Override
            public synchronized void deleteObserver(Observer o) {
                mouseHandler_Observers.remove(o);
            }
        };

        leTileManager = new TileManager(){

            @Override
            public Tile[][] getTilesOverlay() {
                return tileManager_GetTilesOverlay;
            }

            @Override
            public int getRows() {
                return tileManager_GetRows;
            }

            @Override
            public int getColumns() {
                return tileManager_GetColumns;
            }

            @Override
            public double getHeight() {
                return tileManager_GetHeight;
            }

            @Override
            public double getWidth() {
                return tileManager_GetWidth;
            }
        };

        //Our Overlay is 2x2 with the following config
        // PATH SCENERY
        // PATH SCENERY
        tileManager_GetTilesOverlay = new Tile[2][2];
        tileManager_GetTilesOverlay[0][0] = new Tile(SpriteType.PATH,32,32,new Vector2(0,0));
        tileManager_GetTilesOverlay[1][0] = new Tile(SpriteType.SCENERY,32,32,new Vector2(32,0));
        tileManager_GetTilesOverlay[0][1] = new Tile(SpriteType.PATH, 32,32,new Vector2(0,32));
        tileManager_GetTilesOverlay[1][1] = new Tile(SpriteType.SCENERY, 32, 32, new Vector2(32,32));
        tileManager_GetRows = 2;
        tileManager_GetColumns = 2;
        tileManager_GetWidth = 64;
        tileManager_GetHeight = 64;

        leGameManager = GameManager.getInstance();
        leGameManager.initialize(leCanvas.getGraphicsContext2D(), leMouseHandler, leTileManager);
    }


    @Test
    public void constructor_SubscribesToEventsTest(){
        assertTrue(mouseHandler_Observers.contains(leGameManager));
    }

    @Test
    public void update_RightClickClearTest(){
        //Setup
        mouseState_GetMouseState.setPosition(150,150);
        mouseState_GetMouseState.setEventType(MouseEventType.RIGHT_CLICK);

        //Run
        runUpdate();

        //Check
        assertNull(leMouseHandler.getMouseState().getSelectedSprite());
    }

    @Test
    public void update_SelectTowerToBuy_EnoughCurrencyTest(){
        //Setup
        Tower towerToSelect = leGameManager.sideBar.getTowersAvailable()[0];
        int towerCost = towerToSelect.getCost();
        leGameManager.sideBar.setAvailableGold(towerCost);
        Vector2 towerPosition = towerToSelect.getPosition();
        mouseState_GetMouseState.setPosition(towerPosition.getX() + 1, towerPosition.getY() + 1);
        mouseState_GetMouseState.setEventType(MouseEventType.LEFT_CLICK);

        //Run
        runUpdate();

        //Check
        assertEquals(mouseState_GetMouseState, leMouseHandler.getMouseState());
        assertNotNull(mouseState_GetMouseState.getSelectedSprite());
        assertEquals(towerToSelect, mouseState_GetMouseState.getSelectedSprite());
    }

    @Test
    public void update_SelectTowerToBuy_NotEnougCurencyTest(){
        //Setup
        Tower towerToSelect = leGameManager.sideBar.getTowersAvailable()[0];
        leGameManager.sideBar.setAvailableGold(0);
        leGameManager.refreshCanBuyTowers();
        Vector2 towerPosition = towerToSelect.getPosition();
        mouseState_GetMouseState.setPosition(towerPosition.getX() + 1, towerPosition.getY() + 1);
        mouseState_GetMouseState.setEventType(MouseEventType.LEFT_CLICK);

        //Run
        runUpdate();

        //Check
        assertEquals(mouseState_GetMouseState, leMouseHandler.getMouseState());
        assertNull(mouseState_GetMouseState.getSelectedSprite());
    }

    @Test
    public void refreshCanBuyTowersTest(){
        leGameManager.sideBar.setAvailableGold(0);
        leGameManager.refreshCanBuyTowers();
        for (Tower tower :
                leGameManager.sideBar.getTowersAvailable())  {
            assertFalse(tower.isCanBuy());
        }
        leGameManager.sideBar.setAvailableGold(1000);
        leGameManager.refreshCanBuyTowers();
        for (Tower tower :
                leGameManager.sideBar.getTowersAvailable()){
            assertTrue(tower.isCanBuy());
        }
    }

    /*@Test
    public void update_PlaceBoughtTowerTest(){
        Tower towerToSelect = leGameManager.sideBar.getTowersAvailable()[0];
        int towerCost = towerToSelect.getCost();
        leGameManager.sideBar.setAvailableGold(towerCost);
        Vector2 towerPosition = towerToSelect.getPosition();
        mouseState_GetMouseState.setPosition(towerPosition.getX() + 1, towerPosition.getY() + 1);
        mouseState_GetMouseState.setEventType(MouseEventType.LEFT_CLICK);
        runUpdate();

        //Selected tower, now clicking on scenery tile
        Tile leTowerPlacement = tileManager_GetTilesOverlay[1][0];
        mouseState_GetMouseState.setPosition(leTowerPlacement.getPosition().getX() + 1, leTowerPlacement.getPosition().getY() + 1);
        mouseState_GetMouseState.setEventType(MouseEventType.LEFT_CLICK);
        runUpdate();

        //Tower should be placed on top of the scenery tile
        Tile leTowerTile = leTileManager.getTilesOverlay()[1][0];
        assertNotNull(leTowerTile);
        assertTrue(Tower.class.isInstance(leTowerTile));
    }*/


    private void runUpdate() {
        leGameManager.update(leMouseHandler, mouseState_GetMouseState);
    }
}