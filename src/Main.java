import common.Settings;
import game.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import map.MapManager;

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

        /*GameManager gameManager = new GameManager(canvas.getGraphicsContext2D(), scene);
        gameManager.start();*/

        MapManager mapManager = new MapManager(canvas.getGraphicsContext2D(), scene, rows, columns);
        mapManager.start();

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
