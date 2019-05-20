package Gui.AddWindow;


import Server.DataBase.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author stsendom, stweifel
 * This is the FXML Controller that handles the connection between Logic and Gui
 */
public class AddControler implements Initializable {
    @FXML
    private Button cancleBtn;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label Fach;
    @FXML
    private Label Schublade;
    @FXML
    private Label Komponist;
    @FXML
    private Label Titel;
    @FXML
    private TextField RaumField;
    @FXML
    private TextField FachField;
    @FXML
    private TextField SchubladeField;
    @FXML
    private TextField KomponistField;
    @FXML
    private TextField TitelField;
    @FXML
    private TextArea infoArea;

    private boolean readyToAdd;
    private String[] options = {"Raum", "Schublade", "Fach", "Noten"};

    /**
     * Closes the window
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void quit(MouseEvent event) {
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close(); //Close the window
    }

    /**
     * Change the visible content
     *
     * In base of that, what in the combobox is selected, change the visibility of the input textareas
     *
     * @param event type Action Event; Standard parameter
     */
    public void comboBoxChange(ActionEvent event){ //Change visibility in base of the selected item in the combobox
        if (comboBox.getSelectionModel().getSelectedIndex() == 1){
            Schublade.setVisible(true);
            SchubladeField.setVisible(true);
        }else{
            Schublade.setVisible(false);
            SchubladeField.setVisible(false);
        }

        if (comboBox.getSelectionModel().getSelectedIndex() == 2){
            Schublade.setVisible(true);
            SchubladeField.setVisible(true);

            Fach.setVisible(true);
            FachField.setVisible(true);
        }else{
            Fach.setVisible(false);
            FachField.setVisible(false);
        }

        if (comboBox.getSelectionModel().getSelectedIndex() == 3){
            Schublade.setVisible(true);
            SchubladeField.setVisible(true);

            Fach.setVisible(true);
            FachField.setVisible(true);

            Komponist.setVisible(true);
            KomponistField.setVisible(true);

            Titel.setVisible(true);
            TitelField.setVisible(true);
        }else{
            Komponist.setVisible(false);
            KomponistField.setVisible(false);

            Titel.setVisible(false);
            TitelField.setVisible(false);
        }
    }

    /**
     * Add the input to the database
     *
     * Add the input give nin the input textfield to the database
     *
     * @param event type MpuseEvent; Standard parameter
     */
    public void add(MouseEvent event){
        if (!readyToAdd) {
            infoArea.setText("");
            infoArea.setText("Etwas scheint schief gelaufen zu sein!\nÜberprüfen sie die Felder links, ob Sie überall etwas eingegeben haben und ob diese korrekt sind.");
            return;
        }
        if(comboBox.getSelectionModel().getSelectedIndex() == 0){ //Create entry in base of combobox select
            Commands.createRoom(RaumField.getText());
        }else if (comboBox.getSelectionModel().getSelectedIndex() == 1){
            Commands.createBox(SchubladeField.getText(), RaumField.getText());
        }else if (comboBox.getSelectionModel().getSelectedIndex() == 2){
            Commands.createTray(FachField.getText(), SchubladeField.getText(), RaumField.getText());
        }else{
            Commands.createMusicNotes(TitelField.getText(), KomponistField.getText(), FachField.getText(), SchubladeField.getText(), RaumField.getText());
        }
        readyToAdd = false;
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close(); //Closes the window
    }

    /**
     * Check if input is valid
     *
     * Check if input is valid.
     * The inputeded Room, Box or Tray must exist
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void addToDb(MouseEvent event) {
        if (comboBox.getSelectionModel().getSelectedIndex() == 0){ //Check if it is possible to a entry to the database
            if (RaumField.getText() == null){
                infoArea.setText("");
                infoArea.setText("Hinweis: Nicht alle Felder wurden ausgefüllt!\n\tStellen Sie sicher, dass alle ausgefüllt sind!");
                readyToAdd = false;
            }
            readyToAdd = true;
        }else if (comboBox.getSelectionModel().getSelectedIndex() == 1){
            if (RaumField.getText() == null || SchubladeField.getText() == null){
                infoArea.setText("");
                infoArea.setText("Hinweis: Nicht alle Felder wurden ausgefüllt!\n\tStellen Sie sicher, dass alle ausgefüllt sind!");
                readyToAdd = false;
            }
            if (Commands.search("Room", RaumField.getText()).length == 0){
                readyToAdd = false;
            }else{
                readyToAdd = true;
            }
        }else if (comboBox.getSelectionModel().getSelectedIndex() == 2){
            if (RaumField.getText() == null || SchubladeField.getText() == null || FachField.getText() == null){
                infoArea.setText("");
                infoArea.setText("Hinweis: Nicht alle Felder wurden ausgefüllt!\n\tStellen Sie sicher, dass alle ausgefüllt sind!");
                readyToAdd = false;
            }
            if (Commands.search("Room", RaumField.getText()).length == 0 && Commands.search("Box", SchubladeField.getText()).length == 0){
                readyToAdd = false;
            }else{
                readyToAdd = true;
            }
        }else if (comboBox.getSelectionModel().getSelectedIndex() == 3){
            if (RaumField.getText() == null || SchubladeField.getText() == null || FachField.getText() == null || KomponistField.getText() == null){
                infoArea.setText("");
                infoArea.setText("Hinweis: Nicht alle Felder wurden ausgefüllt!\n\tStellen Sie sicher, dass alle ausgefüllt sind!");
                readyToAdd = false;
            }
            if (Commands.search("Room", RaumField.getText()).length == 0 && Commands.search("Box", SchubladeField.getText()).length == 0 && Commands.search("Tray", FachField.getText()).length == 0){
                readyToAdd = false;
            }else{
                readyToAdd = true;
            }
        }
    }

    /**
     * Adds all Items to the combobox
     *
     * @param location type URL; Standard parameter
     * @param resources type ResourceBundle; Standard parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll(options);
        comboBox.getSelectionModel().select(0);
    }
}