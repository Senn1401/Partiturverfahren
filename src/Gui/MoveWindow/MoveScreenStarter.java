package Gui.MoveWindow;

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
public class MoveScreenStarter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the new window
     *
     * @param primaryStage type Stage; Standard parameter
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Stage secondStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Move.fxml")); //Load FXML to GUI
            root.getStylesheets().add((getClass().getResource("StyleSheet.css")).toExternalForm()); //Load Css to GUI
            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.setResizable(false);
            secondStage.initStyle(StageStyle.UTILITY);
            secondStage.setTitle("Verschieben");
            secondStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
