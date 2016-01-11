package common;

import common.core.Sprite;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Created by Sahidul Islam
 */
public class Tile extends Sprite {
    public Tile(double width, double height, Vector2 position) {
        super(width, height, position);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.web("#1c1a1a"));
        gc.strokeRect(position.getX(), position.getY(), width, height);
    }

    @Override
    public void update() {
    }

    private void init(String value) {
        Rectangle border = new Rectangle(50, 50);
        border.setFill(null);
        border.setStroke(Color.web("#1c1a1a"));

        Text text = new Text(value);
        text.setFont(Font.font(30));
        text.setStroke(Color.web("#3f0e0e"));
        //text.setStroke(Color.web("#9d0000"));
    }
}
