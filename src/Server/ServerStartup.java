package Server;

import Gui.Gui.GuiStarter;
import Server.DataBase.Commands;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author stsendom
 * The class is to create a database connection and start the Gui
 */
public class ServerStartup {
    private static Connection conn = null;
    private ServerSocket serverSocket = null;
    private Socket socke = null;
    private final int port = 3306; //Einfach irgenteine Nummer genommen

     /**
     * The Method that creates the database connection and starts the Gui
     */
    public void start(){
       try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //Load the mySQL Driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=SennHD123"); //Connect to the mySQL server on localhost with the user root and the password root
            Commands.setConnection(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GuiStarter g = new GuiStarter();
        g.main(null); //Start the GUI
    }

    /**
     * Connection getter
     * @return type Connection; returns the database connection
     */
    public static Connection getConnection(){
        return conn;
    }
}