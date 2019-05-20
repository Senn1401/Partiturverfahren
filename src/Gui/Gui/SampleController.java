package Gui.Gui;

import Gui.AddWindow.AddScreenStarter;
import Gui.MoveWindow.MoveScreenStarter;
import Gui.RemoveWindow.RemoveScreenStarter;
import Server.DataBase.Commands;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
/**
 * @author stsendom, stweifel
 * This is the FXML Controller that handles the connection between Logic and Gui
 */
public class SampleController implements Initializable {
    @FXML
    private Button addBtn;
    @FXML
    private Button sandwitchMenubutton;
    @FXML
    private Button removeButton;
    @FXML
    private Button moveButton;
    @FXML
    private AnchorPane addBtnAnchor;
    @FXML
    private AnchorPane moveBtnAnchor;
    @FXML
    private AnchorPane removeBtnAnchor;
    @FXML
    private TextArea infoText;
    @FXML
    private TextField showRoomField;
    @FXML
    private TextField searchField;
    @FXML
    private AnchorPane midContainer;
    @FXML
    private AnchorPane infoBoxHandler;
    @FXML
    private Slider containerSlider;
    @FXML
    private TextArea boxInfo;

    private int roomCount = 1;
    private ArrayList<String[]> roomContent = new ArrayList<>();
    private ArrayList<String> suggestions = new ArrayList();
    private String[] rooms;
    private Object[] commandRooms;

    /**
     * The method is to select the input
     *
     * Select the input from the textfield
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void searchMouse(MouseEvent event){
        search();
    }

    /**
     * The method is to select the input
     *
     * Select the input from the textfield
     *
     * @param event type KeyEvent; Standart parameter
     */
    public void searchKey(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            search();
        }
    }

    /**
     * Display infromation form database
     *
     * Take the input. Search with it further informations in the database and dislplay them
     */
    private void search(){
        String titel = searchField.getText().substring(searchField.getText().indexOf(':') + 1,searchField.getText().indexOf(';')).trim();
        String afterTitel = searchField.getText().substring(searchField.getText().indexOf(';') + 1);
        String composer = afterTitel.substring(afterTitel.indexOf(':') + 1).trim();
        String[][] informations = Commands.search2Params("Titel", titel, "Composer", composer); //Get informations from database
        boxInfo.setText("Titel: " + informations[0][1] + "\nKomponist: " + informations[0][2] + "\nRaum: " + informations[0][3] + "\nSchublade: " + informations[0][5] + "\nFach: " + informations[0][4]); //Display informations
    }

    /**
     * Select the room before
     *
     * @param event type MouseEvent; Standard parameter
     */
    public void leftRoom(MouseEvent event){ //Go to previus room
        roomCount--;
        if (roomCount == 0){
            roomCount = rooms.length;
        }
        loadRoom();
    }

    /**
     * Select the next room
     *
     * @param event type MoseEvent; Standard parameter
     */
    public void rightRoom(MouseEvent event){ //Go to next room
        roomCount++;
        if (roomCount != rooms.length){
            roomCount = roomCount % rooms.length;
        }
        loadRoom();
    }

    /**
     * Load the selected room
     *
     * Get the informations of the actual room from database
     */
    private void loadRoom() { //Load the room
        this.roomContent.clear();
        commandRooms = Commands.getRooms(); //Get all rooms
        if (rooms == null){
            rooms = Arrays.copyOf(commandRooms, commandRooms.length, String[].class);
        }
        if(rooms.length == 0){
            return;
        }
        String[][] content = Commands.search("Room", rooms[roomCount - 1]); //Search in database in base of title and composer
        for (String[] i : content){ //Add the content to arraylist if titel and composer isn't null
            if (!i[1].equals("null") && !i[2].equals("null")){
                roomContent.add(i);
            }
        }
        showRoomField.clear();
        showRoomField.setText(rooms[roomCount - 1]);
        containerSlider.setValue(0);
        containerSlider.setMax(roomContent.size());
        fillInfo(); //Display informations
        fillBoxInfo((int) containerSlider.getValue());
    }

    /**
     * Display musicnotes details
     *
     * Display the informations of the actual selected musicnodes
     *
     * @param index type Interger; index of the actual selected room
     */
    private void fillBoxInfo(int index) {
        if (roomContent.size() <= 0){
            return;
        }
        boxInfo.setText("Titel: " + roomContent.get(index)[1] + "\nKomponist: " + roomContent.get(index)[2] + "\nRaum: " + roomContent.get(index)[3] + "\nSchublade: " + roomContent.get(index)[5] + "\nFach: " + roomContent.get(index)[4]); //Display informations
    }

    /**
     * Displasy the actual informations about the room
     */
    private void fillInfo() { //Display informations
        StringBuilder text = new StringBuilder();
        for (String[] row : this.roomContent){
            text.append("Titel: " + row[1] + "\nKomponist: " + row[2] + "\nRaum: " + row[3] + "\nSchublade: " + row[5] + "\nFach: " + row[4] + "\n-----------------------------------\n");
        }
        infoText.setText(text.toString());
    }

    /**
     * Display the actual musicnode selected with the scrollbar
     *
     * @param event type MouseEvent; standard parameter
     */
    public void scrollHandler(MouseEvent event){
        fillBoxInfo((int) containerSlider.getValue());
    }

    /**
     * Open the add window
     *
     * @param event type MouseEvent; standard parameter
     */
    public void addBtnHandler(MouseEvent event){
        AddScreenStarter a = new AddScreenStarter(); //Open add windwo
        a.start(null);
    }

    /**
     * Open the move window
     *
     * @param event type MouseEvent; standard parameter
     */
    public void moveBtnHandler(MouseEvent event){
        MoveScreenStarter m = new MoveScreenStarter(); //Open move windwo
        m.start(null);
    }

    /**
     * Open the remove window
     *
     * @param event type MoseEvent; standard parameter
     */
    public void removeBtnHandler(MouseEvent event){
        RemoveScreenStarter r = new RemoveScreenStarter(); //Open remove windwo
        r.start(null);
    }


    /**
     * Change visibilities
     *
     * Show or hide the add button, the remove button and the move button
     *
     * @param event type MouseEvent; standard parameter
     */
    public void vBoxHandler(MouseEvent event){ //Open and close sandwitchmenu
        if (addBtn.isVisible()){
            addBtn.setVisible(false);
            moveButton.setVisible(false);
            removeButton.setVisible(false);
            addBtnAnchor.setVisible(false);
            moveBtnAnchor.setVisible(false);
            removeBtnAnchor.setVisible(false);
        }else{
            addBtn.setVisible(true);
            moveButton.setVisible(true);
            removeButton.setVisible(true);
            addBtnAnchor.setVisible(true);
            moveBtnAnchor.setVisible(true);
            removeBtnAnchor.setVisible(true);
        }
    }

    /**
     * Set start visiblities, load initial room, create autocomplete
     *
     * @param location type URL; standard parameter
     * @param resources type ResourceBundle; standard parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createSuggestions();
        TextFields.bindAutoCompletion(searchField, suggestions);
        loadRoom();
        sandwitchMenubutton.setVisible(true);
        addBtn.setVisible(false);
        moveButton.setVisible(false);
        removeButton.setVisible(false);
        addBtnAnchor.setVisible(false);
        moveBtnAnchor.setVisible(false);
        removeBtnAnchor.setVisible(false);
    }

    /**
     * Create suggestions for autocomplete
     *
     * Take informations from database and add them to the autocomplete
     */
    private void createSuggestions() {
        String[][] content = Commands.printTable();
        for (String[] i : content){
            if (!i[1].equals("null") && !i[2].equals("null")){
                suggestions.add("Titel: " + i[1] + "; Komponist: " + i[2]);
            }
        }
    }
}
