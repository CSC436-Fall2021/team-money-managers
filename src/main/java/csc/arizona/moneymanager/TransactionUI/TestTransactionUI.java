package csc.arizona.moneymanager.TransactionUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Tests the UI for adding transactions. DIsplays a window containing the transactions pane.
 */
public class TestTransactionUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        TransactionUI test = new TransactionUI();


        stage.setTitle("Test transaction pane");
        stage.setScene(new Scene(test, 500, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
