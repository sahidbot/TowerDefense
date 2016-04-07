package mainmenu.controllers;

import common.Helper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import map.MapManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for handling map editor
 * @version $revision $
 */
public class CreateMapDialogController implements Initializable {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtRows;
    @FXML
    private TextField txtColumns;
    private static final Logger LOGGER = Logger.getLogger(CreateMapDialogController.class);

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
        txtRows.textProperty().addListener((observable, oldValue, newValue) -> {
            LOGGER.info("Detected change in txtRows to: " + newValue);
            checkNumericValue(txtRows, oldValue, newValue);
        });

        txtColumns.textProperty().addListener((observable, oldValue, newValue) -> {
            LOGGER.info("Detected change in txtColumns to: " + newValue);
            checkNumericValue(txtColumns, oldValue, newValue);
        });

        /*txtName.setText("Sample Map");
        txtRows.setText("20");
        txtColumns.setText("24");*/
    }

    /**
     *
     * @param mouseEvent Reference to the control whose event is fired
     */
    public void onCreateNewClicked(MouseEvent mouseEvent) {
        LOGGER.info("Create New Map Clicked");
        if (!txtName.getText().equals("") &&
                !txtRows.getText().equals("") &&
                !txtColumns.getText().equals("")) {
            try {
                LOGGER.info("Reading new map info");
                int rows = Integer.parseInt(txtRows.getText());
                int columns = Integer.parseInt(txtColumns.getText());
                String mapName = txtName.getText();

                LOGGER.info("Checking if columns and rows are withing boundaries");
                if (columns < 10 || columns > 30 || rows < 18 || rows > 24) {
                    LOGGER.warn("Detected columns or rows out of range. columns: " + columns + " rows: " + rows);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tower Defense");
                    alert.setHeaderText("Column can be between 10-30 and rows can be between 18-24");
                    alert.showAndWait();
                    return;
                }

                LOGGER.info("Initializing window");
                Stage stage = new Stage();
                Group root = new Group();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);

                LOGGER.info("Initializing MapManager");
                MapManager mapManager = MapManager.create(root, mapName, rows, columns);
                mapManager.start();

                LOGGER.info("Showing window");
                stage.show();
                stage.setHeight(stage.getHeight() - 12);

                stage.setOnCloseRequest(event -> {
                    try {
                        LOGGER.info("Detected close request. Reading candidate to map data");
                        String mapData = mapManager.getMapData();
                        if (mapData != null) {
                            LOGGER.info("Detected valid map data");
                            Helper.saveMap(mapName, mapData);

                            LOGGER.info("Map saved successfully!");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Tower Defense");
                            alert.setHeaderText("Map saved successfully!");
                            alert.showAndWait();

                            ((Stage) txtName.getScene().getWindow()).close();
                        }
                        else {
                            LOGGER.error("Unsucessful! Message: Invalid map");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Tower Defense");
                            alert.setHeaderText("Invalid map, cannot save!");
                            alert.showAndWait();
                        }
                    }
                    catch (Exception ex) {
                        LOGGER.error("Error while reading map data. Message: "+ ex.getMessage());
                        System.out.println(ex);
                    }
                });
            }
            catch (Exception ex) {
                LOGGER.error("Error while reading map information. Message: "+ ex.getMessage());
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * method to check that entered values
     * for rows and columns are numeric
     * @param text textfield whose value is to be set
     * @param oldValue old value of textfield
     * @param newValue new value that is to be checked for numeric
     */
    public Boolean checkNumericValue(TextField text, String oldValue, String newValue) {
        try {
            if (newValue.matches("\\d*")) {
                LOGGER.info("Reading values entered for map creation. Value= "+newValue);
                int value = Integer.parseInt(newValue);
                return true;
            } else {
                text.setText(oldValue);
                return false;
            }
        }
        catch (Exception ex) {
            LOGGER.error("Error while reading values. Message: "+ ex.getMessage());
            System.out.println(ex.toString());
            return false;
        }
    }
}
