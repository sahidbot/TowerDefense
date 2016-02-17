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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahidul Islam.
 */
public class SideBar {
    private double leftOffset;
    private double topOffset;
    private Vector2 sampleTextPos;
    private boolean inspectionHidden = true;

    private List<Tile> tiles;

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

    public void draw(GraphicsContext gc) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.draw(gc);
            Helper.drawText(gc, "X", tile.getPosition(), Color.RED);
        }
    }

    public void setActive(SpriteType type, boolean value) {
        for (Tile tile : tiles) {
            if (tile.getType() == type) {
                tile.setActive(value);
                break;
            }
        }
    }
}
