package Gui.RemoveWindow;

import Server.DataBase.Commands;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * @author stsendom, stweifel
 * This is the FXML Controller that handles the connection between Logic and Gui
 */
public class RemoveController  implements Initializable {
    @FXML
    private Button cancleBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TextField searchField;
    @FXML
    private TextArea infoArea;

    private String title;
    private String composer;
    private ArrayList<String> suggestions = new ArrayList<>();
    private String[][] content;

    /**
     * Closes window
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void quit(MouseEvent event){
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close(); //Close window
    }

    /**
     * Removes input from database
     *
     * Removes the input given in the textfield
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void remove(MouseEvent event){
        Commands.delete2Params("Titel", title, "Composer", composer); //Delete in base of title and composer
        Stage stage = (Stage) cancleBtn.getScene().getWindow();
        stage.close(); //Close window
    }

    /**
     * Starting method
     *
     * Creates autocomplete
     *
     * @param location type URL; Standard parameter
     * @param resources type ResourceBundle; Standard parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createSuggestions();
        TextFields.bindAutoCompletion(searchField, suggestions); //Create autocomplete
    }

    /**
     * The Method is used to select input
     *
     * @param event type KeyEvent; Standard parameter
     */
    public void selectKey(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            select();
        }
    }

    /**
     * Select the correct row from database
     */
    private void select(){
        String userSearch = searchField.getText();
        if(userSearch.contains("Titel") && userSearch.contains("Komponist")) {
            this.title = userSearch.substring(userSearch.indexOf('-') + 1, userSearch.indexOf(";")).trim();
            String afterTitle = userSearch.substring(userSearch.indexOf(';'));
            composer = afterTitle.substring(afterTitle.indexOf('-') + 1).trim();
            showInfo(Commands.search2Params("Titel", title, "Composer", composer)); //Display musicnode in base of titel and composer
        }else{
            infoArea.setText("Bitte wählen Sie einen der Vorschläge");
        }
    }

    /**
     * The method is used to display the parameter
     * @param searchResult type two dim Stringarray; Content to be displayed
     */
    private void showInfo(String[][] searchResult) {
        infoArea.setText("Titel: " + searchResult[0][1] + "\nKomponist: " + searchResult[0][2] + "\nRaum: " + searchResult[0][3] + "\nSchublade: " + searchResult[0][4] + "\nFach: " + searchResult[0][5] + "\n"); //Display informations
    }

    /**
     * Create Suggestions
     *
     * It uses the databases, reads everything out and adds it to suggestions
     */
    private void createSuggestions() {
        content = Commands.printTable(); //Get whole table
        for (String[] i : content){
            if (!i[1].equals("null") && !i[2].equals("null")){
                suggestions.add("Titel - " + i[1] + "; Komponist - " + i[2]);
            }
        }
    }
}
