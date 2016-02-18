package mainMenu.controllers;

import common.Helper;
import common.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import map.MapManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for handling map editor
 * @author Team 7
 * @version $revision $
 */
public class CreateMapDialogController implements Initializable {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtRows;
    @FXML
    private TextField txtColumns;

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
        txtRows.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumericValue(txtRows, oldValue, newValue);
        });

        txtColumns.textProperty().addListener((observable, oldValue, newValue) -> {
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
        if (!txtName.getText().equals("") &&
                !txtRows.getText().equals("") &&
                !txtColumns.getText().equals("")) {
            try {
                int rows = Integer.parseInt(txtRows.getText());
                int columns = Integer.parseInt(txtColumns.getText());
                String mapName = txtName.getText();

                if (columns < 10 || columns > 30 || rows < 18 || rows > 24) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tower Defense");
                    alert.setHeaderText("Column can be between 10-30 and rows can be between 18-24");
                    alert.showAndWait();
                    return;
                }

                Stage stage = new Stage();
                Group root = new Group();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);

                MapManager mapManager = MapManager.create(root, mapName, rows, columns);
                mapManager.start();
                stage.show();
                stage.setHeight(stage.getHeight() - 12);

                stage.setOnCloseRequest(event -> {
                    try {
                        String mapData = mapManager.getMapData();
                        if (mapData != null) {
                            Helper.saveMap(mapName, mapData);

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Tower Defense");
                            alert.setHeaderText("Map saved successfully!");
                            alert.showAndWait();

                            ((Stage) txtName.getScene().getWindow()).close();
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Tower Defense");
                            alert.setHeaderText("Invalid map, cannot save!");
                            alert.showAndWait();
                        }
                    }
                    catch (Exception ex) {
                        System.out.println(ex);
                    }
                });
            }
            catch (Exception ex) {
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
    private void checkNumericValue(TextField text, String oldValue, String newValue) {
        try {
            if (newValue.matches("\\d*")) {
                int value = Integer.parseInt(newValue);
            } else {
                text.setText(oldValue);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
