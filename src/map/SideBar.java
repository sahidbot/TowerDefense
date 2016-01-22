package map;

import common.Settings;
import common.SpriteType;
import common.Tile;
import common.core.MouseState;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahidul Islam.
 */
public class SideBar {
    private double leftOffset;
    private double topOffset;

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

        tiles = new ArrayList<Tile>();
        tiles.add(tilePath);
        tiles.add(tileEntry);
        tiles.add(tileExit);
    }

    public void update(MouseState mouseState) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.update(mouseState);

            if (mouseState.getLeftClickPosition().getX() > this.leftOffset &&
                    tile.collidesWith(mouseState.getLeftClickPosition())) {
                mouseState.setSelectedSprite(tile);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.draw(gc);
        }
    }
}
