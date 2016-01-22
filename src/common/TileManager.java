package common;

import common.core.MouseState;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Sahidul Islam
 */
public class TileManager {
    private Tile[][] sceneryTiles;
    private Tile[][] tilesOverlay;
    private int rows;
    private int columns;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double getHeight() {
        return Settings.TILE_HEIGHT * rows;
    }

    public double getWidth() {
        return Settings.TILE_WIDTH * columns;
    }

    public Tile[][] getSceneryTiles() {
        return sceneryTiles;
    }

    public Vector2 getTilePosition(Vector2 position) {
        double x = position.getX() > 0 ? Math.ceil(position.getX() / Settings.TILE_WIDTH) : 0;
        double y = position.getY() > 0 ? Math.ceil(position.getY() / Settings.TILE_HEIGHT) : 0;

        if (x > columns || y > rows)
            return Vector2.getZero();

        return new Vector2(x, y);
    }

    public void createScenery(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        sceneryTiles = new Tile[rows][columns];
        tilesOverlay = new Tile[rows][columns];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                Vector2 position = new Vector2(Settings.TILE_WIDTH * x, Settings.TILE_HEIGHT * y);
                Tile tile = new Tile(SpriteType.SCENERY, Settings.TILE_WIDTH, Settings.TILE_HEIGHT, position);
                sceneryTiles[x][y] = tile;
            }
        }
    }

    public void update(MouseState mouseState) {
        if (!mouseState.getLeftClickPosition().isZero()) {
            Vector2 pos = getTilePosition(mouseState.getLeftClickPosition());
            System.out.println(pos);

            if (mouseState.getSelectedSprite() != null) {
                int x = (int) pos.getX() - 1;
                int y = (int) pos.getY() - 1;

                Tile selectedTile = mouseState.getSelectedSprite(Tile.class);
                Tile tileOverlay = createPlaceableTile(x, y, selectedTile);

                if (tileOverlay != null) {
                    tilesOverlay[x][y] = tileOverlay;

                    // clear out after every drop inside tile zone
                    //mouseState.clearSelectedSprite();
                }
            }
        }
    }

    public void draw(GraphicsContext gc) {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                Tile tile = sceneryTiles[x][y];
                tile.draw(gc);

                Tile tileOverlay = tilesOverlay[x][y];
                if (tileOverlay != null) {
                    tileOverlay.draw(gc);
                }
            }
        }
    }

    private Tile createPlaceableTile(int x, int y, Tile tile) {
        // check if already one exists on that position
        if (tilesOverlay[x][y] != null)
            return null;

        if (tile.getType() == SpriteType.PATH) {
            //if (x > 0 && tilesOverlay[x-1][y] == null)
        }

        // create new tile
        Vector2 position = new Vector2(Settings.TILE_WIDTH * x, Settings.TILE_HEIGHT * y);
        return tile.copy(position);
    }
}
