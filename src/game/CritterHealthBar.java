package game;

import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Health bar for a critter
 */
public class CritterHealthBar {
    private final double width = 28;
    private final double height = 5;

    private Vector2 position;
    private double maxHealthPoints;
    private double healthPoints;

    private Color healthColor;

    /**
     * Default constructor
     *
     * @param position Position of the critter
     * @param healthPoints Health point of the critter
     */
    public CritterHealthBar(Vector2 position, float healthPoints) {
        this.position = position;
        this.maxHealthPoints = healthPoints;
        this.healthPoints = healthPoints;

        this.healthColor = getBlendedColor();
    }

    /**
     * Draw the health bar into the canvas
     *
     * @param gc Graphics context of the canvas
     */
    public void draw(GraphicsContext gc) {
        double x = position.getX() + ((32 - width) / 2);
        double y = position.getY() - 10;

        gc.setStroke(Color.GREEN);
        gc.strokeRect(x, y, width, height);

        double w = (healthPoints * width) / maxHealthPoints;

        gc.setFill(healthColor);
        gc.fillRect(x, y, w, height);
    }

    public void updateHealthPoints(double value) {
        if (this.healthPoints != value) {
            this.healthPoints = value;
            this.healthColor = getBlendedColor();
        }
    }

    /**
     * Get the blended color from red to green
     *
     * @return Returns the color
     */
    private Color getBlendedColor() {
        double value = healthPoints / maxHealthPoints;
        return getBlendedColor(value);
    }

    /**
     * Get the blended color from red to green
     *
     * @param value Value range between 0 to 1
     * @return Returns the color
     */
    private Color getBlendedColor(double value) {
        java.awt.Color color = new java.awt.Color(
                java.awt.Color.HSBtoRGB((float)value/3f, 1f, 1f));
        return Color.rgb(color.getRed(), color.getGreen(), color.getBlue());
    }
}
