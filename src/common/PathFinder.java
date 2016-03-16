package common;

import common.core.Vector2;

import java.util.ArrayList;

/**
 * Implementations to find paths.
 */
public class PathFinder {
    /**
     * Gets the paths for critters to move.
     * @param tiles Grid of the path
     * @param rows Max number of rows of the grid.
     * @param columns Max number of columns of the grid.
     * @return Returns the list of paths from entry point to exit point.
     */
    public static ArrayList<Vector2> getPaths(Tile[][] tiles, int rows, int columns) {
        ArrayList<Vector2> paths = new ArrayList<Vector2>();

        Vector2 start = null;
        Vector2 end = null;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tile = tiles[x][y];
                if (tile != null) {
                    if (tile.getType() == SpriteType.ENTRY_POINT) {
                        start = new Vector2(x, y);
                    }
                    else if (tile.getType() == SpriteType.EXIT_POINT) {
                        end = new Vector2(x, y);
                    }
                }
            }
        }

        if (start == null || end == null)
            return paths;

        Vector2 current = start;

        while (!current.equals(end)) {
        }

        return paths;
    }
}
