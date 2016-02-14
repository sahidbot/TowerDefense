package MainMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import map.MapManager;
import common.Settings;
import javafx.scene.Group;

/**
 * Created by saddamtahir on 2016-02-13.
 */
public class MainMenu {
    public void OpenMenu(Stage primaryStage){
        StackPane root = new StackPane();

        Button createMap = new Button("Create Map");
        createMap.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //primaryStage.close();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Create Map");
                secondaryStage.setResizable(false);
                Group child = new Group();
                Scene childScene = new Scene(child);
                int rows = 16;
                int columns = 16;
                double width = (Settings.TILE_WIDTH * rows) + Settings.SIDEBAR_WIDTH;
                double height = Settings.TILE_HEIGHT * columns;

                Canvas canvas = new Canvas(width, height);
                child.getChildren().add(canvas);

        /*GameManager gameManager = new GameManager(canvas.getGraphicsContext2D(), scene);
        gameManager.start();*/

                MapManager mapManager = new MapManager(canvas.getGraphicsContext2D(), childScene, rows, columns);

                mapManager.start();
                secondaryStage.setScene(childScene);
                secondaryStage.show();
                secondaryStage.setHeight(secondaryStage.getHeight() - 12);
            }
        });
        Button editMap = new Button("Edit Map");
        Button startMap = new Button("Start Game");
        HBox hBox = HBoxBuilder.create()
                .spacing(30.0) //In case you are using HBoxBuilder
                .padding(new Insets(5, 5, 5, 5))
                .children(createMap, editMap,startMap)
                .build();

        hBox.setSpacing(30.0); //In your case
        root.getChildren().add(hBox);
        //root.getChildren().add(editMap);
        //root.getChildren().add(startMap);
        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.show();

        // primaryStage.setHeight(primaryStage.getHeight() - 12);
    }


}


