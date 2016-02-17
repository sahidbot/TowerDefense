package map;

import common.Settings;
import common.SpriteType;
import common.Tile;
import common.TileManager;
import common.core.*;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

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
    private double width;
    private double height;
    private Vector2 mousePosition = Vector2.getZero();

    public MapManager(GraphicsContext gc, MouseHandler mouseHandler, int rows, int columns) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        this.mouseHandler = mouseHandler;
        this.mouseHandler.addObserver(this);

        this.tileManager = new TileManager();
        this.tileManager.createScenery(rows, columns);

        this.sideBar = new SideBar(Settings.TILE_WIDTH, Settings.TILE_HEIGHT, tileManager.getWidth(), 0);
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

        if (mouseState.getEventType() == MouseEventType.LEFT_CLICK &&
                mouseState.getSelectedSprite() != null) {
            Sprite selectedSprite = mouseState.getSelectedSprite();
            //System.out.println(selectedSprite.getUniqueId());
        }

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
            Vector2 imageOffset = tile.getImageOffset();

            double w = tile.getWidth();
            double h = tile.getHeight();
            double sx = Math.max(this.mousePosition.getX() - (w / 2), 0);
            double xy = Math.max(this.mousePosition.getY() - (h / 2), 0);

            gc.drawImage(tile.getImage(), imageOffset.getX(), imageOffset.getY(), w, h,
                    sx, xy, tile.getWidth(), tile.getHeight());
        }
    }
}
