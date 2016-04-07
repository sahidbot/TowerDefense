package mainmenu.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.Helper;
import common.Settings;
import game.GameManager;
import game.gamestate.GameState;
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
import map.MapManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main to handle operation from main menu UI
 * @version $revision $
 */
public class MainMenuController implements Initializable{
    @FXML
    private Button btnCreateMap;
    @FXML
    private Button btnEditMap;
    @FXML
    private Button btnStartGame;
    @FXML
    private Button btnLoadGame;
    @FXML
    private ListView mapListView;

    private ObservableList<String> savedMaps;
    private static final Logger maplog = Logger.getLogger(MainMenuController.class);

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
        btnLoadGame.setDisable(true);

        savedMaps = FXCollections.observableArrayList();
        loadSavedMapList();
        mapListView.setItems(savedMaps);
    }
    /**
     * Create Map button callback
     * @param mouseEvent Reference to the control whose event is fired
     */
    public void onCreateMapClicked(MouseEvent mouseEvent) throws IOException {
        maplog.info("Create Map Button Clicked");
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/mainmenu/views/createMapDialogView.fxml"));
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    /**
     * Edit map button callback
     * @param mouseEvent Reference to the control whose event is fired
     */
    public void onEditMapClicked(MouseEvent mouseEvent) {
        maplog.info("Edit Map Button Clicked");
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

                        maplog.info("Map saved successfully!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tower Defense");
                        alert.setHeaderText("Map saved successfully!");
                        alert.showAndWait();

                        loadSavedMapList();
                    }
                    else {
                        maplog.info("Unsucessful! Message: Invalid map");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Tower Defense");
                        alert.setHeaderText("Invalid map, cannot save!");
                        alert.showAndWait();
                    }
                });
            }
        }
    }

    /**
     * Callback for start game button
     * @param mouseEvent Reference to the control whose event is fired
     */
    public void onStartGameClicked(MouseEvent mouseEvent) throws IOException {
        loadGameManager(false);
    }

    /**
     * Method to get selected file from listview
     * @param event Reference to the control whose event is fired
     */
    public void onListViewClicked(MouseEvent event) {
        String selectedMap = (String) mapListView.getSelectionModel().getSelectedItem();
        btnEditMap.setDisable(selectedMap == null);
        btnStartGame.setDisable(selectedMap == null);

        // load
        File file = new File(Settings.USER_GAME_STATE_DIRECTORY + "/" + selectedMap);
        btnLoadGame.setDisable(!file.exists());

        maplog.info(selectedMap.toString()+" was selected");
    }
    /**
     * Method to load saved maps in the directory
     */
    private void loadSavedMapList() {
        final File folder = new File(Settings.USER_MAP_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        for (File map : listOfFiles) {
            savedMaps.add(map.getName());
        }
        maplog.info(folder.getPath()+" loaded");

    }

    /**
     * Callback for load game button
     * @param mouseEvent Reference to the control whose event is fired
     */
    public void onLoadGameClicked(MouseEvent mouseEvent) throws IOException {
        loadGameManager(true);
    }

    /**
     * Load the game manager
     *
     * @param loadSavedGame check whether to load previous state
     */
    private void loadGameManager(boolean loadSavedGame) throws IOException {
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

                GameManager.getInstance().initialize(root, rows, columns, mapData);
                if (loadSavedGame) {
                    GameManager.getInstance().setSaveGameState(loadGameState(selectedMap));
                }
                GameManager.getInstance().start();
                stage.show();
                stage.setHeight(stage.getHeight() - 12);

                stage.setOnCloseRequest(event -> {
                    GameManager.getInstance().stop();
                    saveGameState(selectedMap);
                });
            }
        }
    }

    /**
     * Save the game state to the file
     *
     * @param mapName name of the map
     */
    private void saveGameState(String mapName) {
        GameState gameState = GameManager.getInstance().getSaveGameState();
        if (gameState.towers.size() > 0 ||
                gameState.level > 1) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(gameState);

            Helper.saveGameState(mapName, json);
        }
    }

    /**
     * Load the game state from file
     *
     * @param mapName name of the map
     * @return returns the instance of the game state
     */
    private GameState loadGameState(String mapName) throws IOException {
        File file = new File(Settings.USER_GAME_STATE_DIRECTORY + "/" + mapName);

        if (file.exists()) {
            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            br.close();
            String json = sb.toString();

            GameState gameState = new Gson().fromJson(json, GameState.class);

            return gameState;
        }

        return null;
    }
}
