package csc.arizona.moneymanager.MainUI;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class serves as the entry point to test run the
 * MainUI class.
 *
 * @author Kris Rangel
 */
public class TestMainUI extends Application {

    MainUI mainUI;

    @Override
    public void start(Stage stage) {

        mainUI = new MainUI();
        stage.setTitle("MainUI");
        stage.setScene(mainUI.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
