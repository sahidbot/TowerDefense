package game;

import common.Settings;
import common.core.MouseState;
import common.core.Vector2;
import game.towerlogic.Tower;
import game.towerlogic.TowerType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the side panel that holds the towers and the inspection panel
 * @version $revision $
 */
public class SideBar {

    private final double leftOffset;
    private final double topOffset;
    private final double width;
    private final double inspectionTowerHeight;
    private final double shopTitleWidth;
    private final double height;
    private final double towerWidth;
    private final double defaultMargin;
    private InspectionPanel inspectionPanel;
    private Tower[] towersAvailable;
    private int availableGold;
    private Button newWaveButton;

    public Tower selectedTowerForInspection;

    /**
     * Main Constructor
     *
     * @param width width of the sidebar (panel)
     * @param leftOffset X offset of the scene
     * @param topOffset Y offset of the scene
     */
    public SideBar(double width, double height, double leftOffset, double topOffset) {
        this(width, height, leftOffset, topOffset, Settings.DEFAULT_MARGIN, Settings.TILE_WIDTH,
                100, Settings.FONTSIZE_TITLE + 5);
    }

    /**
     * Constructor mainly done for unit tests
     *
     * @param width width of the sidebar
     * @param height height of the sidebar
     * @param leftOffset X offset of the scene
     * @param topOffset Y offset of the scene
     * @param defaultMargin Default margin, used also as the separation between tiles
     * @param towerWidth Width of the tower sprite
     * @param inspectionTowerHeight Height of the {@link game.InspectionPanel}
     * @param shopTitleWidth title width
     */
    public SideBar(double width, double height, double leftOffset, double topOffset, double defaultMargin, double towerWidth,
                   double inspectionTowerHeight, double shopTitleWidth) {
        this.height = height;
        this.towerWidth = towerWidth;
        this.defaultMargin = defaultMargin;
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        this.width = width;
        this.inspectionTowerHeight = inspectionTowerHeight;
        this.shopTitleWidth = shopTitleWidth;
        inspectionPanel = new InspectionPanel(width, height, leftOffset, height / 2);
        towersAvailable = new Tower[]{
                new Tower(TowerType.ARROW, generateBuyableTowerPosition(1)),
                new Tower(TowerType.FROST, generateBuyableTowerPosition(2)),
                new Tower(TowerType.SIEGE, generateBuyableTowerPosition(3))
        };
        Vector2 newWavePosition = new Vector2(leftOffset + 2,
                topOffset + defaultMargin + shopTitleWidth + defaultMargin + towerWidth + defaultMargin + 2 *Settings.FONTSIZE_LINE + 30);
        newWaveButton = new Button(ButtonType.NEWWAVE, newWavePosition);
    }

    /**
     * Generates a position for the tower
     *
     * @param positionNumber position on the grid, from left to right
     * @return a vector representing the position to use
     */
    public Vector2 generateBuyableTowerPosition(int positionNumber) {
        double x = leftOffset + positionNumber * defaultMargin + (positionNumber - 1) * towerWidth;
        double y = topOffset + defaultMargin + shopTitleWidth + defaultMargin + defaultMargin;
        return new Vector2(x,y);
    }

    /**
     * Draws the sidebar in the scene
     *
     * @param gc the context to draw on
     */
    public void draw(GraphicsContext gc) {

        Font font = Font.font(Settings.FONT_NAME, FontWeight.BOLD, shopTitleWidth - 5);
        gc.setFont(font);

        //DrawShopTitle
        String shopTitle = "**SHOP**";
        Vector2 textPosition = new Vector2(leftOffset + defaultMargin,
                topOffset + defaultMargin + shopTitleWidth);
        drawText(gc, shopTitle, textPosition, Color.BLACK);

        //Draw ShopTowers
        for (Tower tower :
                towersAvailable) {
            tower.draw(gc);
        }

        //Draw availableGold
        Font currencyFont = Font.font(Settings.FONT_NAME, FontWeight.NORMAL, Settings.FONTSIZE_LINE);
        gc.setFont(currencyFont);
        Vector2 currencyPosition = new Vector2(leftOffset + defaultMargin,
                topOffset + defaultMargin + shopTitleWidth + defaultMargin + towerWidth + defaultMargin + Settings.FONTSIZE_LINE + 30);
        String currencyLine = "Available: $" + getAvailableGold();
        drawText(gc, currencyLine, currencyPosition, Color.BLACK);

        //Draw NewWaveButton
        if (!GameManager.getInstance().isGameEnded()) {
            newWaveButton.draw(gc);
        }

        //Draw inspectionPanel
        inspectionPanel.draw(gc);

    }

    /**
     * Draws text in the graphic contexts
     *
     * @param gc target to draw on
     * @param text text to be drawn
     * @param position position of the text
     * @param color color of the text
     */
    private void drawText(GraphicsContext gc, String text, Vector2 position, Color color) {
        gc.setFill(color);
        gc.fillText(text, position.getX(), position.getY());
    }

    /**
     * Method that handles the {@link MouseState}
     *
     * @param mouseState state to react to
     */
    public void update(MouseState mouseState) {

    }

    /**
     * Gets the current availableGold
     *
     * @return current availableGold
     */
    public int getAvailableGold() {
        return availableGold;
    }

    /**
     * Sets the current availableGold
     *
     * @param availableGold
     */
    public void setAvailableGold(int availableGold) {
        this.availableGold = availableGold;
    }

    /**
     * Adds an amount of gold to the total pool
     *
     * @param toAdd gold to add
     */
    public void addAvailableGold(int toAdd){
        setAvailableGold(this.getAvailableGold() + toAdd);
    }

    /**
     * Gets the towers available for buying
     *
     * @return the array of towers
     */
    public Tower[] getTowersAvailable() {
        return towersAvailable;
    }

    /**
     * Gets the inspectionPanel
     *
     * @return inspection panel
     */
    public InspectionPanel getInspectionPanel(){
        return inspectionPanel;
    }

    /**
     * Gets the new wave button.
     * @return The button
     */
    public Button getNewWaveButton() {
        return newWaveButton;
    }
}
