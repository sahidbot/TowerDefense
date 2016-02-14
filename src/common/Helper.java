package common;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Sahidul Islam.
 */
public class Helper {
    public static boolean CheckBound(Vector2 positon, Vector2 boundary) {
        return false;
    }

    public static void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }
}
