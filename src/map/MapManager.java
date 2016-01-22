package map;

import common.Settings;
import common.Tile;
import common.TileManager;
import common.core.*;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Sahidul Islam.
 */
public class MapManager extends GameLoop {
    public MouseHandler mouseHandler;
    public TileManager tileManager;
    public SideBar sideBar;

    private GraphicsContext gc;
    private double width;
    private double height;

    public MapManager(GraphicsContext gc, Scene scene, int rows, int columns) {
        this.gc = gc;
        this.width = gc.getCanvas().getWidth();
        this.height = gc.getCanvas().getHeight();

        mouseHandler = new MouseHandler(scene);

        tileManager = new TileManager();
        tileManager.createScenery(rows, columns);

        sideBar = new SideBar(Settings.TILE_WIDTH, Settings.TILE_HEIGHT, tileManager.getWidth(), 0);
    }

    @Override
    protected void update(double delta) {
        MouseState mouseState = mouseHandler.getMouseState();
        tileManager.update(mouseState);
        sideBar.update(mouseState);

        if (!mouseState.getLeftClickPosition().isZero() &&
                mouseState.getSelectedSprite() != null) {
            Sprite selectedSprite = mouseState.getSelectedSprite();
            System.out.println(selectedSprite.getUniqueId());
        }

        if (!mouseState.getRightClickPosition().isZero()) {
            mouseState.clearSelectedSprite();
        }
    }

    @Override
    protected void clear() {
        mouseHandler.clearMouseState();
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
            double sx = Math.max(mouseState.getMousePosition().getX() - (w / 2), 0);
            double xy = Math.max(mouseState.getMousePosition().getY() - (h / 2), 0);

            gc.drawImage(tile.getImage(), imageOffset.getX(), imageOffset.getY(), w, h,
                    sx, xy, tile.getWidth(), tile.getHeight());
        }
    }
}
