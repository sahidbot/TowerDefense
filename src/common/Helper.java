package common;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.*;

/**
 * This class helps to abstract methods the are commonly used through out all classes
 * @version 1.0.0
 */
public class Helper {
    /**
     * Check the valid boundaries for giver X, Y coordinates.
     *
     * @param x X value of the position.
     * @param y Y value of the position.
     * @param dx X value of the boundary.
     * @param dy Y value of the boundary.
     * @return True for valid and false for invalid.
     */
    public static boolean checkValidBoundaries(int x, int y, int dx, int dy) {
        if (x < 0 || x > dx || y < 0 || y > dy) {
            return false;
        }
        return true;
    }

    /**
     * Draw text on the canvas.
     *
     * @param gc is the graphic context
     * @param text is a text
     * @param position is the text position on canvas
     * @param color is the text color
     */
    public static void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }

    /**
     * Draw mouse icon as tile image.
     *
     * @param gc Graphics context of the canvas.
     * @param tile Tile to get the image.
     * @param mousePosition Position to draw.
     */
    public static void drawMouseIconTile(GraphicsContext gc, Tile tile, Vector2 mousePosition) {
        Vector2 imageOffset = tile.getImageOffset();

        double w = tile.getWidth();
        double h = tile.getHeight();
        double sx = Math.max(mousePosition.getX() - (w / 2), 0);
        double xy = Math.max(mousePosition.getY() - (h / 2), 0);

        gc.drawImage(tile.getImage(), imageOffset.getX(), imageOffset.getY(), w, h,
                sx, xy, tile.getWidth(), tile.getHeight());
    }

    /**
     * method to save created or edited map
     *
     * @param mapName name of map to be saved
     * @param mapContents map file contents as string
     */
    public static void saveMap(String mapName, String mapContents) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(Settings.USER_MAP_DIRECTORY + "/" + mapName));
            out.write(mapContents);
            out.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * method to load selected map to be edited to start game
     *
     * @param mapName name of map to be loaded
     * @return contents of loaded map as string
     */
    public static String loadMap(String mapName) {
        try {
            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(Settings.USER_MAP_DIRECTORY + "/" + mapName));
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            br.close();

            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return null;
    }
    /**
     * Loads selected map into tileManager
     * @param tileManager TileManager object to create map
     * @param mapData Loaded map data as Array of string
     */
    public static void loadTileManagerFromMapData(TileManager tileManager, String[] mapData) {
        try {
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

                tileManager.getTilesOverlay()[x][y] = newTile;
            }

            tileManager.setHasAnyOverlayTile(true);
            tileManager.setHasEntryPointTile(true);
            tileManager.setHasExitPointTile(true);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
