package common;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahidul Islam
 */
public class TileManager {
    private static final double TILE_WIDTH = 50;
    private static final double TILE_HEIGHT = 50;

    private List<Tile> tiles;
    private int rows;
    private int columns;

    public TileManager(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public double getHeight() {
        return TILE_HEIGHT * rows;
    }

    public double getWidth() {
        return TILE_WIDTH * columns;
    }

    public List<Tile> createTiles() {
        /*Pane root = new Pane();
        root.setPrefSize((TILE_HEIGHT * rows) + 1, (TILE_WIDTH * columns) + 1);
        root.setBackground(new Background(new BackgroundFill(Color.web("#303030"), CornerRadii.EMPTY, Insets.EMPTY)));*/

        tiles = new ArrayList<Tile>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Vector2 position = new Vector2(TILE_WIDTH * i, TILE_HEIGHT * j);
                Tile tile = new Tile(TILE_WIDTH, TILE_HEIGHT, position);
                tiles.add(tile);
            }
        }

        return tiles;
    }

    public void draw(GraphicsContext gc) {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).draw(gc);
        }
    }
}
