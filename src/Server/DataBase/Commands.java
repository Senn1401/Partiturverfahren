package Server.DataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author stthaeli, stsendom
 * In this class the database administration takes place, including deleting, adding,
 * search, output table, search for the Highest Room Index.
 */
public class Commands {
    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rset = null;
    private static final String database = "db_newpartiturverfahren";
    private static final String table = "db_data";
    private static final int colums = 6;

    /**
     * In this method we assign the connection and generate a statement.
     *
     * @param conn
     * this parameter defines the connection between the program and the database.
     */
    public static void setConnection(Connection conn) {
        con = conn;
        try {
            stmt = conn.createStatement(); //Create a statement from connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows you to make an entry in the database.
     *
     * An entry contains the parameters listed below, the entry data title and composer are written in lower case,
     * so that the search cannot be key sensitive.
     *
     * @param title type String; Title of the musical piece.
     * @param composer type Stirng; Composer of the musical piece.
     * @param room type String; Room where the musical piece is stored
     * @param box type String; Box is the seperation of a room
     * @param tray type String; Tray is the separation of a box
     * @return Returns a boolean if the adding to the database worked
     */
    private static boolean write(String title, String composer, String room, String box, String tray){
        try {
            String[] content = new String[5]; //If some of the parameters is not null make them lowecase
            if (title != null){
                content[0] = title.toLowerCase();
            }else{
                content[0] = null;
            }
            if (composer != null){
                content[1] = composer.toLowerCase();
            }else{
                content[1] = null;
            }
            if (room != null){
                content[2] = room.toLowerCase();
            }else{
                content[2] = null;
            }
            if (box != null){
                content[3] = box.toLowerCase();
            }else{
                content[3] = null;
            }
            if (tray != null){
                content[4] = tray.toLowerCase();
            }else{
                content[4] = null;
            }
            stmt.execute("USE " + database);
            stmt.executeUpdate("INSERT INTO `"+table+"` (`Titel`, `Composer`, `Room`, `Tray`, `Box`) VALUES ('"+content[0]+"', '"+content[1]+"', '"+content[2]+"', '"+content[3]+"', '"+content[4]+"');"); //Create a new row in the database and write these informations into it
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * The method gets the whole table and returns it
     *
     * The method gets the single table in the database.
     * The informations will be put into a two dim. Stringarray and then returned.
     *
     * @return A two dim. Array of Strings with the whole database in it
     */
    public static String[][] printTable() {
        String[][] result = new String[0][];
        int rows = 0;
        try {
            stmt.execute("USE " + database);
            rset = stmt.executeQuery("SELECT COUNT(*) FROM " + table); //Count erverything from the table
            while (rset.next()){
                result = new String[rset.getInt(1)][6];
            }
            rset = stmt.executeQuery("SELECT * FROM " + table); //Get everything from table
            while (rset.next()){
                for (int i = 0; i < colums; i++){
                    result[rows][i] = rset.getString(i+1);
                }
                rows++;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The method is used to search Informations from the database
     *
     * The method uses in atribut the column name of the table in the database and in inhaltatribut the content or value that will be searched for
     *
     * @param attribut type String; This must be the column name in the database
     * @param inhaltAttribut type String; This value will be searched for
     * @return It returns a two dim Stringarray with all the informations found
     */
    public static String[][] search(String attribut, String inhaltAttribut) {
        int rows = 0;
        String[][] result = new String[0][];
        try {
            stmt.execute("USE " + database);
            rset = stmt.executeQuery("SELECT COUNT(*) FROM "+table+" WHERE "+attribut+" LIKE '"+inhaltAttribut+"';"); //Count everything from table where in the column attribut is inhaltAttribut
            while (rset.next()){
                result = new String[rset.getInt(1)][6];
            }
            rset = stmt.executeQuery("SELECT * FROM " + table + " WHERE " + attribut + " LIKE '" + inhaltAttribut + "';"); //Get everything from table where in the column attribut is inhaltAttribut
            while (rset.next()){
                for (int i = 0; i < colums; i++){
                    result[rows][i] = rset.getString(i+1);
                }
                rows++;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The method is used to search informations from the database with two parqmeter
     *
     * It is build like the normal search but instead of one parameter it uses two.
     * It has two parameters to define in wich column will be searched and two parameters for the content/value thar will be searched for
     *
     * @param atribut1 type String; First column name
     * @param content1 type String, Value that will be searched for in the column with the name in atribut1
     * @param atribut2 type String; Second column name
     * @param content2 type String; Value that will be searched for in the column with the name in atribut2
     * @return It returns a two dim Stringarray with all the informations found
     */
    public static String[][] search2Params(String atribut1, String content1, String atribut2, String content2){
        String[][] result = new String[0][];
        int rows = 1;
        try {
            stmt.execute("USE " + database);
            rset = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE " + atribut1 + " LIKE '" + content1 + "' AND " + atribut2 + " LIKE '" + content2 + "';"); //Count everything from table where  the column attribut1 is content1 and where the cloumn column2 is content2
            while (rset.next()){
                result = new String[rset.getInt(1)][6];
            }
            rset = stmt.executeQuery("SELECT * FROM " + table + " WHERE " + atribut1 + " LIKE '" + content1 + "' AND " + atribut2 + " LIKE '" + content2 + "';"); //Get everything from table where  the column attribut1 is content1 and where the cloumn attribute2 is content2
            while (rset.next()){
                for (int i = 0; i < colums; i++){
                    result[rows - 1][i] = rset.getString(i + 1);
                }
                rows++;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The methode is used to delete content from the database
     *
     * The method deletes the value given in parameter inhaltAttribut from column name attribut
     *
     * @param attribut type String; Column name
     * @param inhaltAttribut type String; Content/value that will be deleted
     * @return It returns a boolean if the delete worked
     */
    public static boolean delete(String attribut, String inhaltAttribut) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM "+table+" WHERE " + attribut + " like '" + inhaltAttribut + "';"); //Delete row from database where the column attribut is inhaltAttribut
        String myDeleteQuery = sb.toString();
        try {
            stmt.execute("USE " + database);
            stmt.executeUpdate(myDeleteQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The method deletes the content based on two parameter
     *
     * The methode deletes the content witch has in column name given in attribut1 the contnent1 and in the column name given in attribut2 the content2
     *
     * @param attribut1 type String; First content name
     * @param content1 type String; Content/value that will be deleted from column with the name given in attribut1
     * @param attribut2 type String; Second column name
     * @param content2 type String; Content/value that will be deleted from column with the name given in attribut2
     * @return It returns a boolean if the delete worked
     */
    public static boolean delete2Params(String attribut1, String content1, String attribut2, String content2) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM "+table+" WHERE " + attribut1 + " like '" + content1 + "' AND " + attribut2 + " LIKE '" + content2 + "';"); //Delete row from database where the column attribut1 is content and where the column attribut2 is content2
        String myDeleteQuery = sb.toString();
        try {
            stmt.execute("USE " + database);
            stmt.executeUpdate(myDeleteQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The method is used to get all roomnames
     *
     * The method takes all roomnames from the database.
     * It does not return a roomanme multiple times
     *
     * @return Returns a Array with all roomnames in it
     */
    public static Object[] getRooms(){
        try {
            Statement stmt1 = con.createStatement();
            int cols = 1;
            ArrayList<String> result = new ArrayList<>();
            stmt1.execute("USE " + database);
            rset = stmt1.executeQuery("SELECT DISTINCT Room FROM " + table + ";"); //Get all rooms but none multiple times
            while (rset.next()){
                result.add(rset.getString(1));
            }
            return result.toArray();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The method creates a room
     *
     * @param room type String; roomname
     */
    public static void createRoom(String room){
        write(null, null, room, null, null);
    }

    /**
     * The method creates a box
     *
     * @param box type String; The boxname
     * @param room type String; The room where the box is in
     */
    public static void createBox(String box, String room) {
        write(null, null, room, box, null);
    }

    /**
     * The method creates a tray
     *
     * @param tray type String; The trayname
     * @param box type String; The box where the tray is in
     * @param room type String; The room where the tray is in
     */
    public static void createTray(String tray, String box, String room) {
        write(null, null, room,box,tray);
    }

    /**
     * The method creats a musicnodefolder
     *
     * @param title type String; The titlename
     * @param composer type String; The name of the composer
     * @param tray type String; The tray where the musiknode is in
     * @param box type String; The box where the musiknode is in
     * @param room type String; The room where the musiknode is in
     */
    public static void createMusicNotes(String title, String composer, String tray, String box, String room) { write(title, composer, room, box, tray);}

    /**
     * The method is used to replace content in the database
     *
     * The method takes a the parameter title and composer to get the correct musicnode.
     * There will be replaced at the parameter newValuePos the value with the newValue
     *
     * @param title type String; the title of the musicnode
     * @param composer type String; the composer of the musicnode
     * @param newValuePos type Integer; the position from 1 to 6 which will be replaced
     * @param newValue type String; the Value that will be inserted
     */
    public static void replace(String title, String composer, int newValuePos, String newValue){
        try {
            if (newValuePos > 5 || newValuePos < 0){
                return;
            }
            stmt.execute("USE " + database);
            String[][] nodes = search2Params("Titel", title, "Composer", composer);
            delete2Params("Titel", title, "Composer", composer);
            if (newValuePos == 1){
                write(newValue, nodes[0][2], nodes[0][3], nodes[0][4], nodes[0][5]);
            }else if (newValuePos == 2){
                write(nodes[0][1], newValue, nodes[0][3], nodes[0][4], nodes[0][5]);
            }else if (newValuePos == 3){
                write(nodes[0][1], nodes[0][2], newValue, nodes[0][4], nodes[0][5]);
            }else if (newValuePos == 4){
                write(nodes[0][1], nodes[0][2], nodes[0][3], newValue, nodes[0][5]);
            }else if (newValuePos == 5){
                write(nodes[0][1], nodes[0][2], nodes[0][3], nodes[0][4], newValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
