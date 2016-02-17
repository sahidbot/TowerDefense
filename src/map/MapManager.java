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
 * Created by Sahidul Islam.
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

    public static MapManager create(Group root, String name, int rows, int columns) {
        double width = (Settings.TILE_WIDTH * columns) + Settings.SIDEBAR_WIDTH;
        double height = Settings.TILE_HEIGHT * rows;

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        return new MapManager(canvas.getGraphicsContext2D(), new MouseHandler(root.getScene()), name, rows, columns);
    }

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

    public void loadMapData(String[] mapData) {
        try {
            for (int i = 1; i < mapData.length; i++) {
                if (mapData[i] == null || mapData[i] == "") {
                    continue;
                }

                String[] parts = mapData[i].split(":");

                int sIndex = parts[0].indexOf(",");
                int x = Integer.parseInt(parts[0].substring(0, sIndex));
                int y = Integer.parseInt(parts[0].substring(sIndex + 1, parts[0].length()));

                SpriteType type = SpriteType.valueOf(parts[1]);
                Vector2 position = new Vector2(Settings.TILE_WIDTH * x, Settings.TILE_HEIGHT * y);
                Tile newTile = new Tile(type, Settings.TILE_WIDTH, Settings.TILE_HEIGHT, position);

                tileManager.getTilesOverlay()[x][y] = newTile;
            }

            tileManager.setHasAnyOverlayTile(true);
            tileManager.setHasEntryPointTile(true);
            tileManager.setHasExitPointTile(true);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
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

    @Override
    protected void update(double delta) {
    }

    @Override
    protected void clear() {
        gc.clearRect(0, 0, width, height);
    }

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
