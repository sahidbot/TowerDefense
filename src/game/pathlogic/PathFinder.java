package game.pathlogic;

import common.SpriteType;
import common.Tile;

import java.util.LinkedList;
import java.util.List;

/**
 * A class to find shortest path from entry point to exit point.
 */
public class PathFinder {
    private Tile[][] tiles;
    public int rows, columns;

    /**
     * Default constructor
     * @param tiles Grid of the path
     * @param rows Max number of rows of the grid.
     * @param columns Max number of columns of the grid.
     */
    public PathFinder(Tile[][] tiles, int rows, int columns) {
        this.tiles = tiles;
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * Gets the paths for critters to move.
     * @return Returns the list of paths from entry point to exit point.
     */
    public LinkedList<Tile> getPaths() {
        Graph<Tile> graph = new Graph<Tile>();

        Tile start = null;
        Tile end = null;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tile = tiles[x][y];
                if (tile != null) {
                    if (tile.getType() == SpriteType.ENTRY_POINT) {
                        start = tile;
                    }
                    else if (tile.getType() == SpriteType.EXIT_POINT) {
                        end = tile;
                    }
                    else if (tile.getType() != SpriteType.PATH) {
                        continue;
                    }

                    addEdge(graph, tile, x+1, y);
                    addEdge(graph, tile, x-1, y);
                    addEdge(graph, tile, x, y+1);
                    addEdge(graph, tile, x, y-1);
                }
            }
        }

        Search<Tile> search = new Search<>();
        List<LinkedList<Tile>> paths = search.depthFirst(graph, start, end);

        // pick the shortest path
        LinkedList<Tile> minPath = paths.get(0);
        for (LinkedList<Tile> path: paths) {
            if (path.size() < minPath.size()) {
                minPath = path;
            }
        }

        return minPath;
    }

    private void addEdge(Graph<Tile> graph, Tile tile, int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= columns)
        {
            return;
        }

        Tile tile2 = tiles[x][y];
        if (tile2 != null) {
            graph.addEdge(tile, tile2);
        }
    }
}
