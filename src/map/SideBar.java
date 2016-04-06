package map;

import common.Helper;
import common.Settings;
import common.SpriteType;
import common.Tile;
import common.core.MouseEventType;
import common.core.MouseState;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class for SideBar. Contains methods related 
 * to manage sidebar items, drawing text
 * @version $revision $
 */
public class SideBar {
    private double leftOffset;
    private double topOffset;
    private Vector2 sampleTextPos;
    private boolean inspectionHidden = true;
    private static final Logger sidebarlog = Logger.getLogger(SideBar.class);

    private List<Tile> tiles;
    /**
     * Constructor to initialize SideBar
     * @param tileWidth The width of the tile
     * @param tileHeight The height of the tile
     * @param leftOffset Left offset value for tile
     * @param topOffset Top offset value for tile
     */
    public SideBar(double tileWidth, double tileHeight, double leftOffset, double topOffset) {
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;

        Vector2 position = new Vector2(leftOffset + Settings.DEFAULT_MARGIN, topOffset);
        Tile tilePath = new Tile(SpriteType.PATH, tileWidth, tileHeight, position);

        position = new Vector2(position.getX() + tileWidth + Settings.DEFAULT_MARGIN, position.getY());
        Tile tileEntry = new Tile(SpriteType.ENTRY_POINT, tileWidth, tileHeight, position);

        position = new Vector2(position.getX() + tileWidth + Settings.DEFAULT_MARGIN, position.getY());
        Tile tileExit = new Tile(SpriteType.EXIT_POINT, tileWidth, tileHeight, position);

        sampleTextPos = new Vector2(leftOffset + Settings.DEFAULT_MARGIN, position.getY() + tileHeight + Settings.DEFAULT_MARGIN + 50);

        tiles = new ArrayList<Tile>();
        tiles.add(tilePath);
        tiles.add(tileEntry);
        tiles.add(tileExit);
    }
    /**
     * Method to update the state of SideBar
     * according to current mouse state
     * @param mouseState The current state of mouse
     */
    public void update(MouseState mouseState) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.update(mouseState);

            if (mouseState.getEventType() == MouseEventType.LEFT_CLICK) {
                if (mouseState.getPosition().getX() > this.leftOffset &&
                        tile.collidesWith(mouseState.getPosition())) {
                    if (tile.isActive()) {
                        mouseState.setSelectedSprite(tile);
                    }
                }
            }
        }
    }
    /**
     * Method to draw graphics in SideBar
     * @param gc The {@link javafx.scene.canvas.GraphicsContext} to use. All graphics will be placed here
     */
    public void draw(GraphicsContext gc) {
        sidebarlog.info("SideBar is drawn");
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.draw(gc);
            Helper.drawText(gc, "X", tile.getPosition(), Color.RED);
        }
    }
    /**
     * Method to enable/disable tiles in SideBar
     * @param type The tile whose state is to be changed
     * @param value True/False. To Set state according to the value.
     */
    public void setActive(SpriteType type, boolean value) {
        sidebarlog.info("The SpriteType "+type+" is set to active");
        for (Tile tile : tiles) {
            if (tile.getType() == type) {
                tile.setActive(value);
                break;
            }
        }
    }
}
