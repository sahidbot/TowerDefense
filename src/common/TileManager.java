package common;

import common.core.MouseEventType;
import common.core.MouseState;
import common.core.Vector2;
import game.towerlogic.Tower;
import javafx.scene.canvas.GraphicsContext;

/**
 * Holds the tiles of type Tile(2dim array) of sceneryTiles and tilesOverlay drawn on the canvas
 * It also hols the rows and columns of type int
 * @version $revision $
 */

public class TileManager implements ITileManager {
    private Tile[][] sceneryTiles;
    private Tile[][] tilesOverlay;
    private int rows;
    private int columns;
    private boolean hasAnyOverlayTile;
    private boolean hasEntryPointTile;
    private boolean hasExitPointTile;

    public TileManager(){
    }

    public TileManager(int rows, int columns, String[] mapData){
        createScenery(rows, columns);

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

            tilesOverlay[x][y] = newTile;
        }

        setHasAnyOverlayTile(true);
        setHasEntryPointTile(true);
        setHasExitPointTile(true);
    }

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
                    tileOverlay.draw(gc);
                }
            }
        }

        // towers
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tileOverlay = tilesOverlay[x][y];
                if (tileOverlay != null &&
                        tileOverlay instanceof Tower) {
                    ((Tower) tileOverlay).draw(gc);
                }
            }
        }
    }
    /**
     * @return Returns tile overlay coordinates
     */
    public Tile[][] getTilesOverlay() {
        return tilesOverlay;
    }
    /**
     * @return Return if tile has overlay
     */
    public boolean hasAnyOverlay() {
        return hasAnyOverlayTile;
    }
    /**
     * @return Return if map has entry point
     */
    public boolean hasEntryPoint() {
        return hasEntryPointTile;
    }
    /**
     * @return Return if map has exit point
     */
    public boolean hasExitPoint() {
        return hasExitPointTile;
    }
    /**
     * Sets value of hasAnyOverlayTile property
     * @param hasAnyOverlayTile Value to be set
     */
    public void setHasAnyOverlayTile(boolean hasAnyOverlayTile) {
        this.hasAnyOverlayTile = hasAnyOverlayTile;
    }
    /**
     * Sets value of hasEntryPointTile property
     * @param hasEntryPointTile Value to be set
     */
    public void setHasEntryPointTile(boolean hasEntryPointTile) {
        this.hasEntryPointTile = hasEntryPointTile;
    }
    /**
     * Sets value of hasExitPointTile property
     * @param hasExitPointTile Value to be set
     */
    public void setHasExitPointTile(boolean hasExitPointTile) {
        this.hasExitPointTile = hasExitPointTile;
    }
    /**
     * Method to create tile which can pe placed
     * @param x X coordinate value
     * @param y Y coordinate value
     * @param tile Tile object which is to be placed
     */
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
    /**
     * Method to validate boundaries of tile
     * @param x X coordinate value
     * @param y Y coordinate value
     */
    public boolean checkValidBoundaries(int x, int y) {
        return Helper.checkValidBoundaries(x, y, this.columns - 1, this.rows - 1);
    }

    /**
     * Clear the towers
     */
    public void clearTowers() {
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tileOverlay = tilesOverlay[x][y];
                if (tileOverlay != null &&
                        tileOverlay instanceof Tower) {
                    tilesOverlay[x][y] = null;
                }
            }
        }
    }

    /**
     * Method to validate if tile is valid
     * @param x X coordinate value
     * @param y Y coordinate value
     * @param type Sprite Type object containing sprite of tile
     */
    private boolean matchValidTile(int x, int y, SpriteType type) {
        return checkValidBoundaries(x, y) && tilesOverlay[x][y] != null &&
                tilesOverlay[x][y].getType() == SpriteType.PATH;
    }
}
