package map;

import common.*;
import common.core.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * Main class for handling operation on Map editor
 * Implemented as Observer {@link java.util.Observer}
 * @version $revision $
 *
 */
public class MapManager extends GameLoop implements Observer {
    public MouseHandler mouseHandler;
    public TileManager tileManager;
    public SideBar sideBar;

    private GraphicsContext gc;
    private String mapName;
    private double width;
    private double height;
    private Vector2 mousePosition = Vector2.getZero();
/**
* Constructor to initializze Map
* @param gc The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
* @param mouseHandler The mouse handler to user input
* @param name The name of the Map
* @param rows The number of rows in the Map
* @param columns The number of columns in the Map
*/
    public MapManager(GraphicsContext gc, MouseHandler mouseHandler, String name, int rows, int columns) {
        this.gc = gc;
        this.mapName = name;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        this.mouseHandler = mouseHandler;
        this.mouseHandler.addObserver(this);

        this.tileManager = new TileManager();
        this.tileManager.createScenery(rows, columns);

        this.sideBar = new SideBar(Settings.TILE_WIDTH, Settings.TILE_HEIGHT, tileManager.getWidth(), 0);
    }
/**
* Static constructor to draw Map
* @param root The {@link javafx.scene.Group} to use
* @param name The name of the Map
* @param rows The number of rows in the Map
* @param columns The number of columns in the Map
*/
    public static MapManager create(Group root, String name, int rows, int columns) {
        double width = (Settings.TILE_WIDTH * columns) + Settings.SIDEBAR_WIDTH;
        double height = Settings.TILE_HEIGHT * rows;

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        return new MapManager(canvas.getGraphicsContext2D(), new MouseHandler(root.getScene()), name, rows, columns);
    }
/**
* Method to get data of saved Maps that is to be loaded.
* @return Contents of saved map as a string
*/
    public String getMapData() {
        if (tileManager.hasAnyOverlay() &&
                tileManager.hasEntryPoint() &&
                tileManager.hasExitPoint())
        {
            StringBuilder sb = new StringBuilder();

            sb.append(this.tileManager.getColumns() + "," + this.tileManager.getRows());
            sb.append(System.getProperty("line.separator"));

            for (int x = 0; x < tileManager.getColumns(); x++) {
                for (int y = 0; y < tileManager.getRows(); y++) {
                    Tile tileOverlay = tileManager.getTilesOverlay()[x][y];
                    if (tileOverlay != null) {
                        sb.append(x + "," + y + ":");
                        sb.append(tileOverlay.getType().toString());
                        sb.append(System.getProperty("line.separator"));
                    }
                }
            }

            return sb.toString();
        }

        return null;
    }
/**
* Loads the Map in TileManager
* @param mapData Map data as Array of string
*/
    public void loadMapData(String[] mapData) {
        Helper.loadTileManagerFromMapData(tileManager, mapData);
    }

    /**
     * This method is called whenever the mouse state is changed.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        MouseState mouseState = (MouseState) arg;

        tileManager.update(mouseState);
        sideBar.update(mouseState);

        if (tileManager.hasEntryPoint()) {
            sideBar.setActive(SpriteType.ENTRY_POINT, false);
        }

        if (tileManager.hasExitPoint()) {
            sideBar.setActive(SpriteType.EXIT_POINT, false);
        }

        if (mouseState.getEventType() == MouseEventType.RIGHT_CLICK) {
            mouseState.clearSelectedSprite();
        }

        if (mouseState.getEventType() == MouseEventType.MOVE) {
            this.mousePosition = mouseState.getPosition();
        }
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
            Helper.drawMouseIconTile(gc, tile, this.mousePosition);
        }
    }
}
