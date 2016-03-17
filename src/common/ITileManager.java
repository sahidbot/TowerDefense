package common;

import common.core.IDrawable;
import common.core.Vector2;

/**
 * Created by Monster on 3/2/2016.
 */
public interface ITileManager extends IDrawable {
    /**
     * Gets the number of rows of the tile manager
     * @return Number of rows
     */
    int getRows();

    /**
     * Gets the number of columns of the tile manager
     * @return Number of columns
     */
    int getColumns();

    /**
     * Gets the height of the tile manager (occupied in the GUI)
     * @return value of height
     */
    double getHeight();

    /**
     * Gets the width of the tile manager (occupied in the GUI)
     * @return value of width
     */
    double getWidth();

    /**
     * Gets the overlay of the tiles, if it is null, it is considered to be grass/empty
     * @return the whole array of tiles
     */
    Tile[][] getTilesOverlay();

    /**
     * Checks to see if there is a tile taht is colliding with the given position
     * @param position position of the mouse in the GUI
     * @return the position of the tile that is colliding with (that might be different than the mouse itself)
     */
    Vector2 getTilePosition(Vector2 position);

    /**
     * Checks if the given position is within the boundaries
     * @param x position of X in space
     * @param y position of Y in space
     * @return whether or not it is within the boundaries
     */
    boolean checkValidBoundaries(int x, int y);

    /**
     * Clear the towers
     */
    void clearTowers();
}
