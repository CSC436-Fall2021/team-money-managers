package csc.arizona.moneymanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

        testPanes();

        stage.setTitle("MainUI");
        stage.setScene(mainUI.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void testPanes(){
        // Test transaction pane
        HBox transactionPane = new HBox();
        transactionPane.setAlignment(Pos.CENTER);
        transactionPane.setPadding(new Insets(20));
        Label transactionLabel = new Label("Transaction");
        HBox.setMargin(transactionLabel, new Insets(20));
        transactionPane.getChildren().addAll(transactionLabel, new TextField("Amount"));
        // Adding test transaction pane to mainUI
        mainUI.setTransactionPane(transactionPane);

        // test Services pane
        BorderPane servicesPane = new BorderPane();
        servicesPane.setPadding(new Insets(20));
        VBox testList = new VBox();
        testList.getChildren().addAll(
                new Label("List item 1"),
                new Label("List item 2"),
                new Label("List item 3"),
                new Label("List item 4")
        );
        FlowPane centerService = new FlowPane();
        Rectangle rect = new Rectangle();
        rect.setWidth(100);
        rect.setHeight(100);
        rect.setStrokeWidth(10);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.AQUAMARINE);
        VBox leftList = new VBox(new Label("Left side services"), new TextField());
        BorderPane.setMargin(leftList, new Insets(20));
        centerService.getChildren().add(rect);
        servicesPane.setCenter(centerService);
        servicesPane.setRight(testList);
        servicesPane.setLeft(leftList);
        // Adding test services pane to mainUI
        mainUI.setServicesPane(servicesPane);

        // Test options pane
        HBox optionsPane = new HBox();
        Label testLabel = new Label("Options Pane");
        HBox.setMargin(testLabel, new Insets(20));
        Button testButton = new Button("Test Button");
        testButton.setOnAction(e-> System.out.println("test button pressed") );

        optionsPane.getChildren().addAll(testLabel, testButton);
        optionsPane.setAlignment(Pos.CENTER);
        optionsPane.setPadding(new Insets(20));

        mainUI.setOptionsPane(optionsPane);
    }

}
