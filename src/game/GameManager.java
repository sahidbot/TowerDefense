package game;

import common.*;
import common.core.*;
import game.towerlogic.Tower;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Observable;
import java.util.Observer;

/**
 * game manager class implemented as observer
 * has method related to gameplay, towers
 * @version $revision $
 */
public class GameManager extends GameLoop implements Observer {
    public ITileManager tileManager;
    public IMouseHandler mouseHandler;
    public SideBar sideBar;

    private GraphicsContext gc;
    private double width;
    private double height;
    private Vector2 mousePosition = Vector2.getZero();

    /**
     * Main constructor for GameManager
     *
     * @param gc    The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
     * @param mouseHandler The mouse handler to user input
     */
    public GameManager(GraphicsContext gc, IMouseHandler mouseHandler, ITileManager tileManager) {
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
    }
    /**
     * Static constructor for GameManager initialize gameplay
     *
     * @param root The {@link javafx.scene.Group} to use.
     * @param rows Number of rows used to determine height of side bar
     * @param columns Number of columns used to determine width of side bar
     * @return GameManager main constructor
     */
    public static GameManager create(Group root, int rows, int columns, String[] mapData) {
        double width = (Settings.TILE_WIDTH * columns) + Settings.SIDEBAR_WIDTH;
        double height = Settings.TILE_HEIGHT * rows;

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        TileManager tileManager = new TileManager(mapData);
        tileManager.createScenery(rows, columns);

        return new GameManager(canvas.getGraphicsContext2D(), new MouseHandler(root.getScene()), tileManager);
    }
    /**
    * {@inheritDoc}
    */
    @Override
    protected void update(double delta) {
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
        sideBar.draw(gc);

        MouseState mouseState = mouseHandler.getMouseState();

        if (mouseState.getSelectedSprite() != null) {
            Tile tile = mouseState.getSelectedSprite(Tile.class);
            if (tile.isDraggable()) {
                Helper.drawMouseIconTile(gc, tile, this.mousePosition);
            }
        }
    }

    /**
     * Method implemented from {@link java.util.Observer} to get notifications from {@link common.core.MouseHandler}
     *
     * @param o   in this case, MouseHandler
     * @param arg arguments that the observable sends.
     */
    @Override
    public void update(Observable o, Object arg) {
        MouseState mouseState = (MouseState) arg;

        if (mouseState.getEventType() == MouseEventType.RIGHT_CLICK) {
            mouseState.clearSelectedSprite();
            sideBar.getInspectionPanel().setSelectedTower(null);
        }

        //Mouse clicked on the side panel
        if (mouseState.getPosition().getX() > tileManager.getWidth()) {
            if (mouseState.getEventType() == MouseEventType.LEFT_CLICK) {
                Tower inspectionPanelTower = sideBar.getInspectionPanel().getSelectedTower();

                // sell and upgrade
                if (inspectionPanelTower != null) {
                    if (sideBar.getInspectionPanel().getSellButton().isEnabled() &&
                            sideBar.getInspectionPanel().getSellButton().collidesWith(mouseState.getPosition())) {
                        // if detected selling tower and sellbutton is clicked
                        sideBar.addAvailableGold(inspectionPanelTower.getRefund());
                        refreshCanBuyTowers();

                        // remove from the tile manager
                        Vector2 tilePosForSelectedTower = tileManager.getTilePosition(inspectionPanelTower.getPosition());
                        int x = (int) tilePosForSelectedTower.getX();
                        int y = (int) tilePosForSelectedTower.getY();
                        tileManager.getTilesOverlay()[x][y] = null;
                        mouseState.setSelectedSprite(null);
                        sideBar.getInspectionPanel().setSelectedTower(null);
                    } else if (sideBar.getInspectionPanel().getUpgradeButton().isEnabled() &&
                            sideBar.getInspectionPanel().getUpgradeButton().collidesWith(mouseState.getPosition())) {
                        // if detected upgrade tower and updatebutton is clicked
                        if (sideBar.getAvailableGold() >= inspectionPanelTower.getCost()) {
                            sideBar.addAvailableGold(-inspectionPanelTower.getCost());
                            inspectionPanelTower.AddLevel(1);
                            refreshCanBuyTowers();
                        }
                    }

                    //Check StrategySwapping
                    else if (sideBar.getInspectionPanel().getLeftArrowButton().isEnabled() &&
                            sideBar.getInspectionPanel().getLeftArrowButton().collidesWith(mouseState.getPosition())) {
                        //collision detected
                        inspectionPanelTower.setAttackStrategyEnum(inspectionPanelTower.getAttackStrategyEnum().next());
                    }
                    else if (sideBar.getInspectionPanel().getRightArrowButton().isEnabled() &&
                            sideBar.getInspectionPanel().getRightArrowButton().collidesWith(mouseState.getPosition())){
                        inspectionPanelTower.setAttackStrategyEnum(inspectionPanelTower.getAttackStrategyEnum().previous());
                    }
                }

                //Check towers to buy collision
                for (Tower tower : sideBar.getTowersAvailable()) {
                    if (tower.collidesWith(mouseState.getPosition())) {
                        //We detected the tower - lets see if we can buy it now
                        if (tower.isCanBuy() && !tower.isActive()) {
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
}
