import game.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Defense Tower");
        primaryStage.setResizable(false);

        Canvas canvas = new Canvas(900, 600);
        root.getChildren().add(canvas);

        GameManager gameManager = new GameManager(canvas.getGraphicsContext2D(), scene);
        gameManager.start();

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
