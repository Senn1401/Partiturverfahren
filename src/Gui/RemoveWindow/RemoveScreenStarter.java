package Gui.RemoveWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author stsendom, stweifel
 * Starter of the connector and it creates the window
 */
public class RemoveScreenStarter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts new window
     *
     * @param primaryStage type Stage; Standard window
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Stage secondStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Remove.fxml")); //Load FXML to GUI
            root.getStylesheets().add((getClass().getResource("StyleSheet.css")).toExternalForm()); //Load Css to GUI
            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.setResizable(false);
            secondStage.initStyle(StageStyle.UTILITY);
            secondStage.setTitle("Löschen");
            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
