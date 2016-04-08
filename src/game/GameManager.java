package game;

import common.Helper;
import common.Settings;
import common.Tile;
import common.TileManager;
import common.core.*;
import game.gamestate.GameState;
import game.gamestate.TowerState;
import game.pathlogic.PathFinder;
import game.towerlogic.Tower;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * game manager class implemented as observer
 * has method related to gameplay, towers
 * @version $revision $
 */
public class GameManager extends GameLoop implements Observer {
    public TileManager tileManager;
    public IMouseHandler mouseHandler;
    public SideBar sideBar;
    public CritterManager critterManager;

    private static GameManager instance;
    private GraphicsContext gc;
    private double width;
    private double height;
    private Vector2 mousePosition = Vector2.getZero();
    private boolean isWaveStarted = false;
    private boolean isGameEnd = false;
    private int level = 1;
    private static final Logger LOGGER = Logger.getLogger(GameManager.class);

    /**
     * Main constructor for GameManager
     */
    private GameManager() {
    }

    /**
     * Get the instance of game manager.
     * @return Returns the instance of game manager.
     */
    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }

    /**
     * Initialize the game manager
     *
     * @param root The {@link javafx.scene.Group} to use.
     * @param rows Number of rows used to determine height of side bar
     * @param columns Number of columns used to determine width of side bar
     * @param mapData Saved content for the map
     */
    public void initialize(Group root, int rows, int columns, String[] mapData) {
        LOGGER.debug("Initializing Game Manager");

        this.width = (Settings.TILE_WIDTH * columns) + Settings.SIDEBAR_WIDTH;
        this.height = Settings.TILE_HEIGHT * rows;

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        this.tileManager = new TileManager(rows, columns, mapData);
        this.critterManager = new CritterManager(new PathFinder(tileManager.getTilesOverlay(), rows, columns));

        this.gc = canvas.getGraphicsContext2D();

        this.mouseHandler = new MouseHandler(root.getScene());
        this.mouseHandler.addObserver(this);

        sideBar = new SideBar(gc.getCanvas().getWidth() - tileManager.getWidth(),
                gc.getCanvas().getHeight(), tileManager.getWidth(), 0);
        sideBar.setAvailableGold(Settings.STARTING_CURRENCY);
        refreshCanBuyTowers();

        isGameEnd = false;
        isWaveStarted = false;
        level = 1;
    }

    /**
     * Initialize the game manager, overloaded method
     *
     * @param gc The graphic context.
     * @param mouseHandler represents the mouse handle to user input
     * @param tileManager The tile manager gets the tile properties
     */
    public void initialize(GraphicsContext gc, IMouseHandler mouseHandler, TileManager tileManager) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        this.mouseHandler = mouseHandler;
        this.mouseHandler.addObserver(this);

        this.tileManager = tileManager;

        sideBar = new SideBar(gc.getCanvas().getWidth() - tileManager.getWidth(),
                gc.getCanvas().getHeight(), tileManager.getWidth(), 0);
        sideBar.setAvailableGold(Settings.STARTING_CURRENCY);
        refreshCanBuyTowers();

        isGameEnd = false;
        isWaveStarted = false;
        level = 1;
    }

    /**
     * Start a new wave
     */
    public void startWave() {
        if (isGameEnd) {
            sideBar.setAvailableGold(Settings.STARTING_CURRENCY);
            refreshCanBuyTowers();
            isGameEnd = false;
            tileManager.clearTowers();
        }

        isWaveStarted = true;
        critterManager.startWave();

        LOGGER.info("Starting new wave");
    }
    /**
     * End the current wave
     */
    public void endWave() {
        isWaveStarted = false;
        this.level++;
        LOGGER.info("Finished wave");
        LOGGER.info("Level upgraded to: " + level);
    }

    /**
     * Get the current game level
     *
     * @return Returns the current level of the game
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * End the current game
     */
    public void endGame() {
        endWave();
        isGameEnd = true;
        LOGGER.info("Game Ended");
    }

    /**
     * Check for game over
     * @return Returns whether game over or not.
     */
    public boolean isGameEnded() {
        return isGameEnd;
    }

    /**
    * {@inheritDoc}
    */
    @Override
    protected void update(double delta) {
        if (isGameEnded()) return;

        sideBar.getNewWaveButton().setEnabled(!isWaveStarted);
        towerShoots(delta);

        if (isWaveStarted) {
            critterManager.update(delta);
        }
    }
    /**
    * Overridden Gameloop clear method to clear contents
    * {@inheritDoc}
    */
    @Override
    protected void clear() {
        mouseHandler.clearMouseState();
        gc.clearRect(0, 0, width, height);
    }
    /**
    * Overridden Gameloop draw method to draw Map and SideBar after each GameLoop iteration
    * {@inheritDoc}
    */
    @Override
    protected void draw() {
        tileManager.draw(gc);
        critterManager.draw(gc);

        sideBar.draw(gc);

        MouseState mouseState = mouseHandler.getMouseState();

        if (mouseState.getSelectedSprite() != null) {
            Tile tile = mouseState.getSelectedSprite(Tile.class);
            if (tile.isDraggable()) {
                Helper.drawMouseIconTile(gc, tile, this.mousePosition);
            }
        }

        if (isGameEnd) {
            Font font = Font.font(Settings.FONT_NAME, FontWeight.BOLD, 60);
            gc.setFont(font);

            //DrawShopTitle
            Vector2 textPosition = new Vector2((tileManager.getWidth() / 2) - 200,
                    (tileManager.getHeight() / 2) - 30);
            Helper.drawText(gc, "GAME OVER", textPosition, Color.RED);
        }
        sideBar.getInspectionPanel().drawTowerLogText(sideBar.getInspectionPanel().getSelectedTower());
    }

    /**
     * Method implemented from {@link java.util.Observer} to get notifications from {@link common.core.MouseHandler}
     *
     * @param o   in this case, MouseHandler
     * @param arg arguments that the observable sends.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (isGameEnded()) return;

        MouseState mouseState = (MouseState) arg;

        if (mouseState.getEventType() == MouseEventType.RIGHT_CLICK) {
            LOGGER.info("Detected right click");
            if (mouseState.getSelectedSprite() != null) {
                LOGGER.info("Clearing selected tower: " + mouseState.getSelectedSprite().getUniqueId());
            }
            mouseState.clearSelectedSprite();
            sideBar.getInspectionPanel().setSelectedTower(null);
        }

        //Mouse clicked on the side panel
        if (mouseState.getPosition().getX() > tileManager.getWidth() && !isWaveStarted) {
            if (mouseState.getEventType() == MouseEventType.LEFT_CLICK) {
                LOGGER.info("Detected " + mouseState.getEventType() + " on side panel");
                Tower inspectionPanelTower = sideBar.getInspectionPanel().getSelectedTower();

                // sell and upgrade
                if (inspectionPanelTower != null) {
                    LOGGER.info("Detected a tower selected");
                    if (sideBar.getInspectionPanel().getSellButton().isEnabled() &&
                            sideBar.getInspectionPanel().getSellButton().collidesWith(mouseState.getPosition())) {
                        // if detected selling tower and sellbutton is clicked
                        LOGGER.info("Detected collission with Sell Button");
                        sideBar.addAvailableGold(inspectionPanelTower.getRefund());
                        LOGGER.info("Refreshing towers that are available for buying");
                        refreshCanBuyTowers();

                        // remove from the tile manager
                        LOGGER.info("Removing tower: " + mouseState.getSelectedSprite().getUniqueId() + " from TileManager");
                        Vector2 tilePosForSelectedTower = tileManager.getTilePosition(inspectionPanelTower.getPosition());
                        int x = (int) tilePosForSelectedTower.getX();
                        int y = (int) tilePosForSelectedTower.getY();
                        tileManager.getTilesOverlay()[x][y] = null;
                        mouseState.setSelectedSprite(null);
                        sideBar.getInspectionPanel().setSelectedTower(null);
                    } else if (sideBar.getInspectionPanel().getUpgradeButton().isEnabled() &&
                            sideBar.getInspectionPanel().getUpgradeButton().collidesWith(mouseState.getPosition())) {
                        // if detected upgrade tower and updatebutton is clicked
                        LOGGER.info("Detected click on Upgrade Button");
                        if (sideBar.getAvailableGold() >= inspectionPanelTower.getCost()) {
                            LOGGER.info("Upgrading tower");
                            inspectionPanelTower.AddLevel(1);
                            LOGGER.info("Updating available gold");
                            sideBar.addAvailableGold(-inspectionPanelTower.getCost());
                            LOGGER.info("Refreshing available towers for buying");
                            refreshCanBuyTowers();
                        }
                        else{
                            LOGGER.info("Not enough currency to buy tower");
                        }
                    }

                    //Check StrategySwapping
                    else if (sideBar.getInspectionPanel().getLeftArrowButton().isEnabled() &&
                            sideBar.getInspectionPanel().getLeftArrowButton().collidesWith(mouseState.getPosition())) {
                        //collision detected
                        LOGGER.info("Detected click on Left Arrow button");
                        inspectionPanelTower.setAttackStrategyEnum(inspectionPanelTower.getAttackStrategyEnum().next());
                    }
                    else if (sideBar.getInspectionPanel().getRightArrowButton().isEnabled() &&
                            sideBar.getInspectionPanel().getRightArrowButton().collidesWith(mouseState.getPosition())){
                        LOGGER.info("Detected click on Right Arrow button");
                        inspectionPanelTower.setAttackStrategyEnum(inspectionPanelTower.getAttackStrategyEnum().previous());
                    }
                }

                //Check NewWaveButton
                if(sideBar.getNewWaveButton().isEnabled() &&
                        sideBar.getNewWaveButton().collidesWith(mouseState.getPosition())){
                    LOGGER.info("Detected click on New Wave button");
                    startWave();
                }

                //Check towers to buy collision
                for (Tower tower : sideBar.getTowersAvailable()) {
                    if (tower.collidesWith(mouseState.getPosition())) {
                        //We detected the tower - lets see if we can buy it now
                        if (tower.isCanBuy() && !tower.isActive()) {
                            LOGGER.info("Detected click on Tower available to buy");
                            mouseState.setSelectedSprite(tower);
                        }

                        //either way, we show it in our inspection panel
                        sideBar.getInspectionPanel().setSelectedTower(tower);
                        break;
                    }
                }
            }
        }

        if (mouseState.getEventType() == MouseEventType.MOVE) {
            // set the mouse position to current
            this.mousePosition.setFromVector(mouseState.getPosition());

            Sprite selectedSprite = mouseState.getSelectedSprite();
            if (selectedSprite != null && selectedSprite instanceof Tower) {
                sideBar.getInspectionPanel().setSelectedTower((Tower) selectedSprite);
            }
            else if (selectedSprite == null) {
                Tower foundTower = null;
                for (Tower tower : sideBar.getTowersAvailable()) {
                    if (tower.collidesWith(mouseState.getPosition())) {
                        LOGGER.info("Detected hovering on tower: " + tower.getTowerHeaderLog());
                        foundTower = tower;
                        break;
                    }
                }
                sideBar.getInspectionPanel().setSelectedTower(foundTower);
            }
        }

        tileManagerUpdate(mouseState);
    }

    /**
    * Method to update state of TileManager. Places new towers on map
    * @param mouseState The mouse handler to user input
    */
    private void tileManagerUpdate(MouseState mouseState) {
        if (mouseState.getEventType() == MouseEventType.LEFT_CLICK) {
            if (mouseState.getSelectedSprite() != null) {
                Vector2 pos = tileManager.getTilePosition(mouseState.getPosition());
                int x = (int) pos.getX() - 1;
                int y = (int) pos.getY() - 1;

                Tower selectedTile = mouseState.getSelectedSprite(Tower.class);
                if (selectedTile != null && !selectedTile.isDraggable())
                    return;

                if (!tileManager.checkValidBoundaries(x, y))
                    return;

                if (tileManager.getTilesOverlay()[x][y] != null)
                    return;

                Vector2 position = new Vector2(Settings.TILE_WIDTH * x, Settings.TILE_HEIGHT * y);
                Tower newTower = new Tower(selectedTile.getTowerType(), position);
                newTower.setActive(true);
                newTower.setDraggable(false);

                tileManager.getTilesOverlay()[x][y] = newTower;

                sideBar.setAvailableGold(sideBar.getAvailableGold() - selectedTile.getCost());
                refreshCanBuyTowers();
                mouseState.clearSelectedSprite();

                LOGGER.debug("Placed a new tower at position: " + position.toString());
            }
        }

        Vector2 mousePos = mouseState.getPosition();

        if (mousePos.getX() <= tileManager.getWidth() ||
                mousePos.getY() <= tileManager.getHeight()) {

            Vector2 pos = tileManager.getTilePosition(mousePos);
            int x = (int) pos.getX() - 1;
            int y = (int) pos.getY() - 1;

            if (x < 0 || y < 0) {
                return;
            }

            if (tileManager.getTilesOverlay()[x][y] == null ||
                    !(tileManager.getTilesOverlay()[x][y] instanceof Tower)) {
                return;
            }

            Tower tower = (Tower) tileManager.getTilesOverlay()[x][y];

            if (mouseState.getEventType() == MouseEventType.MOVE) {
                sideBar.getInspectionPanel().setSelectedTower(tower);
            }
            else if (mouseState.getEventType() == MouseEventType.LEFT_CLICK) {
                mouseState.setSelectedSprite(tower);
            }
        }
    }

    /**
    * Method to check if enough money is available to buy tower
    */
    public void refreshCanBuyTowers() {
        for (Tower tower : sideBar.getTowersAvailable()) {
            tower.setCanBuy(tower.getCost() <= sideBar.getAvailableGold());
        }
    }

    /**
     * Method for shooting Critters
     *
     * @param delta represents the changes in time
     */
    private void towerShoots(double delta) {
        for (Tower leTower : getTowersInScene()) {
            ArrayList<Critter> possibleTargets = critterManager.getShootableCritters(leTower);

            if (leTower.isTimeToFire(delta) && possibleTargets.size() > 0) {
                List<Critter> affectedCritters = leTower.doDamage(critterManager, possibleTargets);
            } else if (possibleTargets.size() == 0) {
                leTower.clearRateOfFire();
            }
        }
        int rewards = critterManager.getRewards();
        if (rewards > 0) {
            LOGGER.info("Collecting rewards");
            sideBar.addAvailableGold(rewards);
        }
        refreshCanBuyTowers();
    }

    /**
     * Method for getting the tower in the scene
     *
     * @return Returns the Tower in the scene
     */
    public List<Tower> getTowersInScene(){
        List<Tower> towersInScene = new ArrayList<>();
        for (Tile[] tileArray:
                tileManager.getTilesOverlay()){
            for (Tile tile :
                    tileArray) {
                if(tile != null && tile instanceof Tower){
                    towersInScene.add((Tower)tile);
                }
            }
        }
        return towersInScene;
    }

    /**
     * Get the game state to serialize
     *
     * @return Returns an instance of the game state
     */
    public GameState getSaveGameState() {
        GameState state = new GameState();
        state.level = getLevel();
        state.availableGold = sideBar.getAvailableGold();

        for (int x = 0; x < tileManager.getColumns(); x++) {
            for (int y = 0; y < tileManager.getRows(); y++) {
                if (tileManager.getTilesOverlay()[x][y] instanceof Tower) {
                    Tower tower = (Tower) tileManager.getTilesOverlay()[x][y];
                    if (tower != null) {
                        TowerState towerState = new TowerState();
                        towerState.id = tower.getUniqueId();
                        towerState.towerType = tower.getTowerType();
                        towerState.strategy = tower.getAttackStrategyEnum();
                        towerState.posX = x;
                        towerState.posY = y;
                        towerState.level = tower.getLevel();

                        state.towers.add(towerState);
                    }
                }
            }
        }

        return state;
    }

    /**
     * Set the game state to resume the game
     *
     * @param state Instance of the game state
     */
    public void setSaveGameState(GameState state) {
        if (state == null) return;

        this.level = state.level;
        sideBar.setAvailableGold(state.availableGold);

        for (TowerState ts : state.towers) {
            Vector2 position = new Vector2(Settings.TILE_WIDTH * ts.posX,
                    Settings.TILE_HEIGHT * ts.posY);
            Tower tower = new Tower(ts.towerType, position);
            tower.setAttackStrategyEnum(ts.strategy);
            tower.setLevel(ts.level);
            tower.setUniqueId(ts.id);

            tower.setActive(true);
            tower.setDraggable(false);

            tileManager.getTilesOverlay()[ts.posX][ts.posY] = tower;
        }
    }
}
