package common;

import javafx.scene.image.Image;

/**
 * This class is used to set or initialize variables such as width, height, margin, image, font, currency e.t.c
 */
public class Settings {
    public static final double TILE_WIDTH = 32;
    public static final double TILE_HEIGHT = 32;

    public static final double DEFAULT_MARGIN = 10;
    public static final double SIDEBAR_WIDTH = 300;

    public static final String USER_MAP_DIRECTORY = "Saved Maps";

    public static final Image BACKGROUND_TILE_IMAGE = new Image("/assets/images/background_tiles.png");
    public static final Image TOWERSIEGE_TILE_IMAGE = new Image("/assets/images/Castle1.png");
    public static final Image TOWERARROW_TILE_IMAGE = new Image("/assets/images/Chapel1.png");
    public static final Image TOWERFROST_TILE_IMAGE = new Image("/assets/images/Temple1.png");
    public static final Image NOMONEY_TILE_IMAGE = new Image("/assets/images/NoMoney.png");
    public static final Image SELLBUTTON_TILE_IMAGE = new Image("/assets/images/SellButton.png");
    public static final Image UPGRADEBUTTON_TILE_IMAGE = new Image("/assets/images/UpgradeButton.png");

    public static final Image CRITTER_TILE_IMAGE_1 = new Image("/assets/images/Critter1.png");

    public static final Image LEFTBUTTON_TILE_IMAGE = new Image("/assets/images/LeftArrowButton.png");
    public static final Image RIGHTBUTTON_TILE_IMAGE = new Image("/assets/images/RightArrowButton.png");
    public static final Image NEWWAVEBUTTON_TILE_IMAGE = new Image("/assets/images/NewWaveButton.png");

    public static final String FONT_NAME = "Courier New";
    public static final int FONTSIZE_TITLE = 28;
    public static final int FONTSIZE_LINE = 16;
    public static final int STARTING_CURRENCY = 1000;

}
