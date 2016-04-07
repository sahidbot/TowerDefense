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
    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class);

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
        LOGGER.info("Initializing");
        LOGGER.info("Setting edit map button to true");
        btnEditMap.setDisable(true);
        LOGGER.info("Setting start game button to true");
        btnStartGame.setDisable(true);
        LOGGER.info("Setting load game button to true");
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
        LOGGER.info("Create Map Button Clicked. Loading stage");
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
        LOGGER.info("Edit Map Button Clicked");
        String selectedMap = (String) mapListView.getSelectionModel().getSelectedItem();
        if (selectedMap != null) {
            String mapContent = Helper.loadMap(selectedMap);
            if (mapContent != null) {
                LOGGER.info("Detected content in the map file");
                String[] mapData = mapContent.split(System.getProperty("line.separator"));

                LOGGER.info("Processing map file");
                int sIndex = mapData[0].indexOf(",");
                int columns = Integer.parseInt(mapData[0].substring(0, sIndex));
                int rows = Integer.parseInt(mapData[0].substring(sIndex + 1, mapData[0].length()));

                LOGGER.info("Map file processed, setting up window");
                Stage stage = new Stage();
                Group root = new Group();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);

                LOGGER.info("Setting up map manager");
                MapManager mapManager = MapManager.create(root, selectedMap, rows, columns);
                mapManager.loadMapData(mapData);
                mapManager.start();

                LOGGER.info("Window ready to show");
                stage.show();
                stage.setHeight(stage.getHeight() - 12);

                stage.setOnCloseRequest(event -> {
                    LOGGER.info("Detected window closing, parsing data to save");
                    String newMapData = mapManager.getMapData();
                    if (newMapData != null) {
                        LOGGER.info("Detected map data to save, saving data into new file");
                        Helper.saveMap(selectedMap, newMapData);

                        LOGGER.info("Map saved successfully!");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tower Defense");
                        alert.setHeaderText("Map saved successfully!");
                        alert.showAndWait();

                        loadSavedMapList();
                    }
                    else {
                        LOGGER.error("Unsucessful! Message: Invalid map");
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
        LOGGER.info("Detected click on ListView");
        String selectedMap = (String) mapListView.getSelectionModel().getSelectedItem();
        if (btnEditMap.isDisable() != (selectedMap == null)) {
            LOGGER.info("Edit map button disable changed to: " + (selectedMap == null));
            btnEditMap.setDisable(selectedMap == null);
        }
        if (btnStartGame.isDisable() != (selectedMap == null)) {
            LOGGER.info("Start game button disable changed to: " + (selectedMap == null));
            btnStartGame.setDisable(selectedMap == null);
        }
        // load
        File file = new File(Settings.USER_GAME_STATE_DIRECTORY + "/" + selectedMap);
        if (btnLoadGame.isDisable() != !file.exists()) {
            LOGGER.info("Load game button disable changed to: " + (!file.exists()));
            btnLoadGame.setDisable(!file.exists());
        }
        LOGGER.info(selectedMap.toString() + " is now selected");
    }
    /**
     * Method to load saved maps in the directory
     */
    private void loadSavedMapList() {
        final File folder = new File(Settings.USER_MAP_DIRECTORY);
        File[] listOfFiles = folder.listFiles();
        LOGGER.info("Detected " + listOfFiles.length + " maps");
        for (File map : listOfFiles) {
            String mapName = map.getName();
            LOGGER.info("Adding map to list: " + mapName);
            savedMaps.add(mapName);
        }
        LOGGER.info(folder.getPath()+" loaded");

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
