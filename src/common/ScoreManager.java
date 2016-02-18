package common;

import common.core.MouseState;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class manages the Level, Health and Money at gameplay
 * Its holds constants of LEVEL, HEALTH, MONEY TITLE
 * @version $revision $
 */
public class ScoreManager {
    private double leftOffset;
    private double topOffset;

    private static final String LEVEL_TITLE = "Level";
    private int level = 0;

    private static final String HEALTH_TITLE = "Health";
    private int health = 0;

    private static final String MONEY_TITLE = "Money";
    private int money = 0;

    private Vector2 mousePosition = Vector2.getZero();
    private Vector2 leftClickPosition = Vector2.getZero();
    private Vector2 rightClickPosition = Vector2.getZero();
    private static final String MOUSE_TITLE = "Mouse";
    private static final String MOUSE_LEFT_CLICK_TITLE = "Mouse Left";
    private static final String MOUSE_RIGHT_CLICK_TITLE = "Mouse Right";

    public ScoreManager(double leftOffset, double topOffset) {
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void updateMousePosition(MouseState mouseState) {
        /*this.mousePosition.setFromVector(mouseState.getMousePosition());

        if (!mouseState.getLeftClickPosition().equals(Vector2.getZero()))
        {
            this.leftClickPosition.setFromVector(mouseState.getLeftClickPosition());
        }

        if (!mouseState.getRightClickPosition().equals(Vector2.getZero()))
        {
            this.rightClickPosition.setFromVector(mouseState.getRightClickPosition());
        }*/
    }

    /**
     * method to display text and other information
     * @param gc graphics context to draw
     */
    public void draw(GraphicsContext gc) {
        Font font = Font.font("Courier New", FontWeight.BOLD, 28);
        gc.setFont(font);

        Vector2 position = new Vector2(leftOffset + 15, topOffset + 30);
        drawText(gc, LEVEL_TITLE, position, Color.RED);

        position.setY(position.getY() + 25);
        drawText(gc, String.valueOf(level), position, Color.BLUE);

        position.setY(position.getY() + 50);
        drawText(gc, HEALTH_TITLE, position, Color.RED);

        position.setY(position.getY() + 25);
        drawText(gc, String.valueOf(health), position, Color.BLUE);

        position.setY(position.getY() + 50);
        drawText(gc, MONEY_TITLE, position, Color.RED);

        position.setY(position.getY() + 25);
        drawText(gc, String.valueOf(money), position, Color.BLUE);


        // mouse
        position.setY(position.getY() + 50);
        drawText(gc, MOUSE_TITLE, position, Color.RED);

        position.setY(position.getY() + 25);
        String mousePos = String.valueOf(mousePosition.getX()) + "," +
                          String.valueOf(mousePosition.getY());
        drawText(gc, mousePos, position, Color.BLUE);

        // mouse left
        position.setY(position.getY() + 50);
        drawText(gc, MOUSE_LEFT_CLICK_TITLE, position, Color.RED);

        position.setY(position.getY() + 25);
        mousePos = String.valueOf(leftClickPosition.getX()) + "," +
                   String.valueOf(rightClickPosition.getY());
        drawText(gc, mousePos, position, Color.BLUE);

        // mouse right
        position.setY(position.getY() + 50);
        drawText(gc, MOUSE_RIGHT_CLICK_TITLE, position, Color.RED);

        position.setY(position.getY() + 25);
        mousePos = String.valueOf(rightClickPosition.getX()) + "," +
                String.valueOf(rightClickPosition.getY());
        drawText(gc, mousePos, position, Color.BLUE);
    }

    /**
     * method to draw text on sidebar
     * @param gc graphic context to draw
     * @param text text to be drawn
     * @param position position at which text will be drawn
     * @param color color of text
     */
    private static void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }
}
