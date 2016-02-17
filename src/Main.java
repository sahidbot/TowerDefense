import MainMenu.MainMenu;
import common.Settings;

import common.core.MouseHandler;
import game.GameManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import map.MapManager;

/** This is the main class which extends Application,
 * It holds the main method which calls the launch method of Application
 * And also overrides the start and stop method of Application class
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Defense Tower");
        primaryStage.setResizable(false);

        int rows = 20;
        int columns = 24;

        double width = (Settings.TILE_WIDTH * columns) + Settings.SIDEBAR_WIDTH;
        double height = Settings.TILE_HEIGHT * rows;

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        /*GameManager gameManager = new GameManager(canvas.getGraphicsContext2D(), scene, rows, columns);
        gameManager.start();*/

        MapManager mapManager = new MapManager(canvas.getGraphicsContext2D(), new MouseHandler(scene), rows, columns);
        mapManager.start();

        /*MainMenu mainMenu = new MainMenu();
        mainMenu.openMenu(primaryStage);*/

        primaryStage.show();
        primaryStage.setHeight(primaryStage.getHeight() - 12);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
