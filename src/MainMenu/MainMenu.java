package MainMenu;

import common.Settings;
import common.core.MouseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import map.MapManager;

import java.io.File;


/**
 * Created by saddamtahir on 2016-02-13.
 */
public class MainMenu {
    public void OpenMenu(Stage primaryStage){
        StackPane root = new StackPane();
        Button createMap = new Button("Create Map");
        ListView<String> fileList = new ListView<String>();
        final File folder = new File("/Users/saddamtahir/Downloads");
        File[] listOfFiles = folder.listFiles();

        ObservableList<String> maps = FXCollections.observableArrayList();
        for (File map:listOfFiles) {
            maps.add(map.getName());
        }
        fileList.setItems(maps);

        /* event to open map dimensions window */
        createMap.setOnAction(e -> {

            Stage mapDimensionStage = new Stage();
            String value = fileList.getSelectionModel().getSelectedItem();
            Label lblRows = new Label("Rows:");
            Label lblCol = new Label("Columns:");
            TextField txtRows = new TextField ();
            TextField txtCols = new TextField();
            Group mapDimensionsGroup = new Group();

            Button btnOpenMapEditor = new Button("Start");

            /* event to open map editor window */
            btnOpenMapEditor.setOnAction(a -> {
                int rows = Integer.parseInt(txtRows.getText());
                int columns = Integer.parseInt(txtRows.getText());
                double width = (Settings.TILE_WIDTH * rows) + Settings.SIDEBAR_WIDTH;
                double height = Settings.TILE_HEIGHT * columns;
                //primaryStage.close();
                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Create Map");
                secondaryStage.setResizable(false);
                Group mapEditorGroup = new Group();
                Scene mapEditorScene = new Scene(mapEditorGroup);

                Canvas canvas = new Canvas(width, height);
                mapEditorGroup.getChildren().add(canvas);

                MapManager mapManager = new MapManager(canvas.getGraphicsContext2D(), new MouseHandler(mapEditorScene), rows, columns);

                mapManager.start();
                secondaryStage.setScene(mapEditorScene);
                secondaryStage.show();
                secondaryStage.setHeight(secondaryStage.getHeight() - 12);
            });

            HBox hb = new HBox();
            hb.getChildren().addAll(lblCol, txtCols,lblRows,txtRows,btnOpenMapEditor);
            hb.setSpacing(10);
            mapDimensionsGroup.getChildren().add(hb);
            Scene mapDimensionsScene = new Scene(mapDimensionsGroup,600,80);
            mapDimensionStage.setScene(mapDimensionsScene);
            mapDimensionStage.show();
        });
        Button editMap = new Button("Edit Map");
editMap.setOnAction(m -> {

//    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Open Resource File");
//    fileChooser.showOpenDialog(stage);
});


        Button startMap = new Button("Start Game");

        HBox hBox = HBoxBuilder.create()
                .spacing(30.0) //In case you are using HBoxBuilder
                .padding(new Insets(5, 5, 5, 5))
                .children(createMap, editMap,startMap,fileList)
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


