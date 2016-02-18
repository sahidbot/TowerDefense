package common;

import common.core.MouseEventType;
import common.core.MouseState;
import common.core.Vector2;
import game.Tower;
import javafx.scene.canvas.GraphicsContext;

/**
 * Holds the tiles of type Tile(2dim array) of sceneryTiles and tilesOverlay drawn on the canvas
 * It also hols the rows and columns of type int
 * @version $revision $
 */

public class TileManager {
    private Tile[][] sceneryTiles;
    private Tile[][] tilesOverlay;
    private int rows;
    private int columns;
    private boolean hasAnyOverlayTile;
    private boolean hasEntryPointTile;
    private boolean hasExitPointTile;

    /**
     * getter for rows
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * getter for columns
     * @return columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     *
     * @return height of type double
     */
    public double getHeight() {
        return Settings.TILE_HEIGHT * rows;
    }

    /**
     * gets the width of a tile
     * @return width of type double
     */
    public double getWidth() {
        return Settings.TILE_WIDTH * columns;
    }

    public Tile[][] getSceneryTiles() {
        return sceneryTiles;
    }

    /**
     * return placed tile position
     *
     * @param position of type vector2
     * @return vector2 coordinates for placed tile
     */
    public Vector2 getTilePosition(Vector2 position) {
        double x = position.getX() > 0 ? Math.ceil(position.getX() / Settings.TILE_WIDTH) : 0;
        double y = position.getY() > 0 ? Math.ceil(position.getY() / Settings.TILE_HEIGHT) : 0;

        if (x > columns || y > rows)
            return Vector2.getZero();

        return new Vector2(x, y);
    }

    /**
     * creates scenery tiles
     *
     * @param rows is the specified rows
     * @param columns is the specified columns
     */
    public void createScenery(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        sceneryTiles = new Tile[columns][rows];
        tilesOverlay = new Tile[columns][rows];

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Vector2 position = new Vector2(Settings.TILE_WIDTH * x, Settings.TILE_HEIGHT * y);
                Tile tile = new Tile(SpriteType.SCENERY, Settings.TILE_WIDTH, Settings.TILE_HEIGHT, position);
                sceneryTiles[x][y] = tile;
            }
        }
    }

    /**
     * method to update mouse state as per selected tile
     * @param mouseState current mouse state
     */
    public void update(MouseState mouseState) {
        if (mouseState.getEventType() == MouseEventType.LEFT_CLICK) {
            Vector2 pos = getTilePosition(mouseState.getPosition());
            //System.out.println(pos);

            if (mouseState.getSelectedSprite() != null) {
                int x = (int) pos.getX() - 1;
                int y = (int) pos.getY() - 1;

                Tile selectedTile = mouseState.getSelectedSprite(Tile.class);
                Tile tileOverlay = createPlaceableTile(x, y, selectedTile);

                if (tileOverlay != null) {
                    tilesOverlay[x][y] = tileOverlay;
                    hasAnyOverlayTile = true;

                    if (tileOverlay.getType() == SpriteType.ENTRY_POINT) {
                        hasEntryPointTile = true;
                    }
                    else if (tileOverlay.getType() == SpriteType.EXIT_POINT) {
                        hasExitPointTile = true;
                    }

                    // clearPosition out after every drop inside tile zone
                    //mouseState.clearSelectedSprite();
                }
            }
        }
    }

    /**
     * method to draw a tile on map
     * @param gc of type graphicContext
     */
    public void draw(GraphicsContext gc) {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tile = sceneryTiles[x][y];
                tile.draw(gc);

                Tile tileOverlay = tilesOverlay[x][y];
                if (tileOverlay != null) {
                    if (tileOverlay instanceof Tower) {
                        ((Tower) tileOverlay).draw(gc);
                    }
                    else {
                        tileOverlay.draw(gc);
                    }
                }
            }
        }
    }

    public Tile[][] getTilesOverlay() {
        return tilesOverlay;
    }

    public boolean hasAnyOverlay() {
        return hasAnyOverlayTile;
    }

    public boolean hasEntryPoint() {
        return hasEntryPointTile;
    }

    public boolean hasExitPoint() {
        return hasExitPointTile;
    }

    public void setHasAnyOverlayTile(boolean hasAnyOverlayTile) {
        this.hasAnyOverlayTile = hasAnyOverlayTile;
    }

    public void setHasEntryPointTile(boolean hasEntryPointTile) {
        this.hasEntryPointTile = hasEntryPointTile;
    }

    public void setHasExitPointTile(boolean hasExitPointTile) {
        this.hasExitPointTile = hasExitPointTile;
    }

    private Tile createPlaceableTile(int x, int y, Tile tile) {
        // check boundaries
        if (!checkValidBoundaries(x, y))
            return null;

        // check if already one exists on that position
        if (tilesOverlay[x][y] != null)
            return null;

        if (hasAnyOverlayTile) {
            if ((tile.getType() == SpriteType.ENTRY_POINT && hasEntryPointTile) ||
                    (tile.getType() == SpriteType.EXIT_POINT && hasExitPointTile))
            {
                return null;
            }

            SpriteType type = tile.getType();

            if (!matchValidTile(x - 1, y, type) &&  !matchValidTile(x, y - 1, type) &&
                    !matchValidTile(x + 1, y, type) && !matchValidTile(x, y + 1, type))
            {
                return null;
            }
        }

        // create new tile
        Vector2 position = new Vector2(Settings.TILE_WIDTH * x, Settings.TILE_HEIGHT * y);
        return tile.copy(position);
    }

    public boolean checkValidBoundaries(int x, int y) {
        return Helper.checkValidBoundaries(x, y, this.columns - 1, this.rows - 1);
    }

    private boolean matchValidTile(int x, int y, SpriteType type) {
        return checkValidBoundaries(x, y) && tilesOverlay[x][y] != null &&
                tilesOverlay[x][y].getType() == SpriteType.PATH;
    }
}
