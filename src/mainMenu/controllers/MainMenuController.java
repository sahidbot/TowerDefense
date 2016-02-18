package mainMenu.controllers;

import common.Helper;
import common.Settings;
import game.GameManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import map.MapManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sahidul on 2/16/2016.
 */
public class MainMenuController implements Initializable{
    @FXML
    private Button btnCreateMap;
    @FXML
    private Button btnEditMap;
    @FXML
    private Button btnStartGame;
    @FXML
    private ListView mapListView;

    private ObservableList<String> savedMaps;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEditMap.setDisable(true);
        btnStartGame.setDisable(true);

        savedMaps = FXCollections.observableArrayList();
        loadSavedMapList();
        mapListView.setItems(savedMaps);
    }

    public void onCreateMapClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/mainMenu/views/createMapDialogView.fxml"));
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void onEditMapClicked(MouseEvent mouseEvent) {
        try {
            String selectedMap = (String) mapListView.getSelectionModel().getSelectedItem();
            if (selectedMap != null) {
                String mapContent = Helper.loadMap(selectedMap);
                if (mapContent != null) {
                    String[] mapData = mapContent.split(System.getProperty("line.separator"));

                    int sIndex = mapData[0].indexOf(",");
                    int columns = Integer.parseInt(mapData[0].substring(0, sIndex));
                    int rows = Integer.parseInt(mapData[0].substring(sIndex + 1, mapData[0].length()));

                    Stage stage = new Stage();
                    Group root = new Group();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);

                    MapManager mapManager = MapManager.create(root, selectedMap, rows, columns);
                    mapManager.loadMapData(mapData);
                    mapManager.start();
                    stage.show();
                    stage.setHeight(stage.getHeight() - 12);

                    stage.setOnCloseRequest(event -> {
                        String newMapData = mapManager.getMapData();
                        if (newMapData != null) {
                            Helper.saveMap(selectedMap, newMapData);

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Tower Defense");
                            alert.setHeaderText("Map saved successfully!");
                            alert.showAndWait();
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Tower Defense");
                            alert.setHeaderText("Invalid map, cannot save!");
                            alert.showAndWait();
                        }
                    });
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void onStartGameClicked(MouseEvent mouseEvent) {
        try {
            String selectedMap = (String) mapListView.getSelectionModel().getSelectedItem();
            if (selectedMap != null) {
                String mapContent = Helper.loadMap(selectedMap);
                if (mapContent != null) {
                    String[] mapData = mapContent.split(System.getProperty("line.separator"));

                    int sIndex = mapData[0].indexOf(",");
                    int columns = Integer.parseInt(mapData[0].substring(0, sIndex));
                    int rows = Integer.parseInt(mapData[0].substring(sIndex + 1, mapData[0].length()));

                    Stage stage = new Stage();
                    Group root = new Group();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);

                    GameManager gameManager = GameManager.create(root, rows, columns);
                    gameManager.loadMapData(mapData);
                    gameManager.start();
                    stage.show();
                    stage.setHeight(stage.getHeight() - 12);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void onListViewClicked(MouseEvent event) {
        String selectedMap = (String) mapListView.getSelectionModel().getSelectedItem();
        btnEditMap.setDisable(selectedMap == null);
        btnStartGame.setDisable(selectedMap == null);
    }

    private void loadSavedMapList() {
        final File folder = new File(Settings.USER_MAP_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        for (File map : listOfFiles) {
            savedMaps.add(map.getName());
        }
    }
}
