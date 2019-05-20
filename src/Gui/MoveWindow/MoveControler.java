package Gui.MoveWindow;

import Server.DataBase.Commands;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author stsendom, stweifel
 * This is the FXML Controller that handles the connection between Logic and Gui
 */
public class MoveControler implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private TextField newValue;
    @FXML
    private Button searchBtn;
    @FXML
    private Button moveBtn;
    @FXML
    private Button cancleBtn;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextArea infoArea;

    private String[] options = {"Titel", "Komponist", "Raum", "Schublade", "Fach"}; //Combobox items
    private ArrayList<String> suggestions = new ArrayList<>();
    String[][] content;

    /**
     * The method quits the window
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void quit(MouseEvent event){
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * The method is used to select the input
     *
     * The method secects the input from the textfield and moves it
     *
     * @param event type KeyEvent; Standard parameter
     */
    public void selectKey(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            select();
        }
    }

    /**
     * The method is used to select the input
     *
     * The method secects the input from the textfield and moves it
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void selectMouse(MouseEvent event){
        select();
    }

    /**
     * The method moves the data in the database
     *
     * The method selects the input in the textfield.
     * It displays the informations and moves it in the dataase.
     */
    private void select(){
        String text = searchField.getText();
        String title = text.substring(text.indexOf('-') + 1, text.indexOf(';')).trim();
        text = text.substring(text.indexOf(';'));
        String composer = text.substring(text.indexOf('-') + 1).trim();
        String[][] searchContent = Commands.search2Params("Titel", title, "Composer", composer); //Search informations from database in base of titel and composer
        for (String[] i : searchContent){ //Display content
            infoArea.setText("Titel: " + i[1]);
            infoArea.appendText("Komponist: " + i[2]);
            infoArea.appendText("Raum: " + i[3]);
            infoArea.appendText("Schublade: " + i[4]);
            infoArea.appendText("Fache: " + i[5]);
            infoArea.appendText("--------------------------------");
        }
        Commands.replace(title, composer, comboBox.getSelectionModel().getSelectedIndex(), searchField.getText());
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close(); //Close window
    }

    /**
     * Starting method
     *
     * Adds the items to the combobox
     *
     * @param location typ URL; Standard parameter
     * @param resources typ ResourceBundle; Standard parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().addAll(options); //Add items to combobox
    }
}
