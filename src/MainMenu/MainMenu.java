package mainMenu;

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

import java.io.*;


/**
 * This class is to create main menu UI layout
 * to select different options. like map editor,
 * start game or edit map
 * @version $revision $
 */
public class MainMenu {
    /**
     * entry point of main menu. Opens menu window
     * @param primaryStage primarystage of type Stage
     */
    public void openMenu(Stage primaryStage) {
        StackPane root = new StackPane();
        Button createMap = new Button("Create Map");
        ListView<String> fileList = new ListView<String>();
        final File folder = new File(Settings.USER_MAP_DIRECTORY);
        File[] listOfFiles = folder.listFiles();

        ObservableList<String> maps = FXCollections.observableArrayList();
        for (File map : listOfFiles) {
            maps.add(map.getName());
        }
        fileList.setItems(maps);

        /* event to open map dimensions window */
        createMap.setOnAction(e -> {
            Stage mapDimensionStage = new Stage();
            String value = fileList.getSelectionModel().getSelectedItem();
            Label lblRows = new Label("Rows:");
            Label lblCol = new Label("Columns:");
            TextField txtRows = new TextField();
            TextField txtCols = new TextField();
            Group mapDimensionsGroup = new Group();

            Button btnOpenMapEditor = new Button("Start");

            /* event to open map editor window */
            btnOpenMapEditor.setOnAction(a -> {

                int rows = 5, columns = 5; // set default columns and rows value
                if (!txtRows.getText().equals("")) {
                    rows = Integer.parseInt(txtRows.getText());
                }
                if (!txtCols.getText().equals("")) {
                    columns = Integer.parseInt(txtCols.getText());
                }

                double width = (Settings.TILE_WIDTH * rows) + Settings.SIDEBAR_WIDTH;
                double height = Settings.TILE_HEIGHT * columns;

                Stage secondaryStage = new Stage();
                secondaryStage.setTitle("Create Map");
                secondaryStage.setResizable(false);
                Group mapEditorGroup = new Group();
                Scene mapEditorScene = new Scene(mapEditorGroup);

                Canvas canvas = new Canvas(width, height);
                mapEditorGroup.getChildren().add(canvas);

                //MapManager mapManager = new MapManager(canvas.getGraphicsContext2D(), new MouseHandler(mapEditorScene), rows, columns);

                //mapManager.start();
                secondaryStage.setScene(mapEditorScene);
                secondaryStage.show();
                secondaryStage.setHeight(secondaryStage.getHeight() - 12);
            });

            HBox hb = new HBox();
            hb.getChildren().addAll(lblCol, txtCols, lblRows, txtRows, btnOpenMapEditor);
            hb.setSpacing(10);

            mapDimensionsGroup.getChildren().add(hb);
            Scene mapDimensionsScene = new Scene(mapDimensionsGroup, 600, 80);
            mapDimensionStage.setScene(mapDimensionsScene);
            mapDimensionStage.show();
        });

        Button editMap = new Button("Edit Map");
        editMap.setOnAction(m -> {

        });


        Button startMap = new Button("Start Game");

        HBox hBox = HBoxBuilder.create()
                .spacing(30.0) //In case you are using HBoxBuilder
                .padding(new Insets(5, 5, 5, 5))
                .children(createMap, editMap, startMap, fileList)
                .build();

        hBox.setSpacing(30.0); //In your case
        root.getChildren().add(hBox);
        //root.getChildren().add(editMap);
        //root.getChildren().add(startMap);
        Scene scene = new Scene(root, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @deprecated moved to helper class
     * @see common.Helper#loadMap(String)
     * @param mapName name of map to be saved
     * @return contents of map to be loaded as string
     * @throws IOException file not found exception
     */
    public static String loadMap(String mapName) throws IOException {
        try {
            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(Settings.USER_MAP_DIRECTORY + "/" + mapName));
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            br.close();

            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return null;
    }

    /**
     * @deprecated moved to helper class
     * @see common.Helper#saveMap(String, String)
     * @param mapName map name
     * @param mapContents map contents
     */
    public static void saveMap(String mapName, String mapContents) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(Settings.USER_MAP_DIRECTORY + "/" + mapName));
            out.write(mapContents);
            out.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}


