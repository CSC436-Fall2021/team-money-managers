package csc.arizona.moneymanager;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class serves as the entry point to test run the
 * MainUI class.
 *
 * @author Kris Rangel
 */
public class TestMainUI extends Application {
    @Override
    public void start(Stage stage) {

        stage.setTitle("MainUI");
        MainUI mainUI = new MainUI();
        stage.setScene(mainUI.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}