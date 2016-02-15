package game;

import common.Settings;
import common.core.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monster on 2/14/2016.
 * Represents the inspection panel that shows the stats of the tower and
 * the sell button is the tower is sellable
 */
public class InspectionPanel {

    private final double width;
    private final double height;
    private final double leftOffset;
    private final double topOffset;
    private Tower selectedTower;

    /**
     * Default constructor
     * @param width width of the inspection panel
     * @param height height of the inspection panel
     * @param leftOffset left offset of the inspection panel
     * @param topOffset top offset of the inspection panel
     */
    public InspectionPanel(double width, double height, double leftOffset, double topOffset) {
        this.height = height;
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        this.width = width;
    }

    /**
     * Gets the selected tower
     * @return current selected tower
     */
    public Tower getSelectedTower() {
        return selectedTower;
    }

    /**
     * Sets the selected tower
     * @param selectedTower new selected tower
     */
    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    /**
     * Draws the inspection panel
     * @param gc target to draw on
     */
    public void draw(GraphicsContext gc) {
        if(getSelectedTower() != null) {
            Font titleFont = Font.font(Settings.FONT_NAME, FontWeight.BOLD, 20);
            Font parametersFont = Font.font(Settings.FONT_NAME, FontWeight.NORMAL, 16);

            float titleSeparation = 24;
            float linesSeparation = 17;

            gc.setFont(titleFont);
            String towerTitle = getSelectedTower().getTowerType().name() + " TOWER";
            Vector2 titlePosition = new Vector2(leftOffset,
                    topOffset);
            drawText(gc, towerTitle, titlePosition, Color.BLACK);

            //Setup stat lines
            List<String> statLines = new ArrayList<String>();
            gc.setFont(parametersFont);
            statLines.add("Level: " + getSelectedTower().getLevel());
            statLines.add("Rate of Fire: " + getSelectedTower().getRateOfFire());
            statLines.add("Range: " + getSelectedTower().getRange());
            statLines.add("Damage: " + getSelectedTower().getDamage());
            if(!selectedTower.isActive()){
                //If tower is not active, we can buy it
                statLines.add("Cost: " + getSelectedTower().getCost());
            }
            else{
                statLines.add("Upgrade Cost: " + getSelectedTower().getCost());
            }

            //draw each line
            for (int i = 0; i < statLines.size(); i++){
                drawText(gc, statLines.get(i),
                        new Vector2(titlePosition.getX(), titlePosition.getY() + (i + 1) * linesSeparation),
                        Color.BLACK);
            }

        }
    }

    private void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }

}
