package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.TransactionUI.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        TransactionUI transactionPane = new TransactionUI(null);
        // Adding test transaction pane to mainUI
        mainUI.setTransactionPane(transactionPane);

        // test Services pane
        BorderPane servicesPane = new BorderPane();
        servicesPane.setPadding(MainUI.PADDING);
        VBox testList = new VBox();
        testList.getChildren().addAll(
                new Label("List item 1"),
                new Label("List item 2"),
                new Label("List item 3"),
                new Label("List item 4")
        );
        FlowPane centerService = new FlowPane();


        ObservableList<PieChart.Data> data =
                FXCollections.observableArrayList(
                        new PieChart.Data("Food", 20),
                        new PieChart.Data("Other", 10));
        PieChart pieChart = new PieChart(data);
        pieChart.setScaleX(0.5);
        pieChart.setScaleY(0.5);
        centerService.getChildren().add(pieChart);

        VBox leftList = new VBox(new Label("Left side services"), new TextField());
        //BorderPane.setMargin(leftList, MainUI.PADDING);
        servicesPane.setCenter(centerService);
        servicesPane.setRight(testList);
        servicesPane.setLeft(leftList);
        // Adding test services pane to mainUI
        mainUI.setServicesPane(servicesPane);

        // Test options pane
        HBox optionsPane = new HBox();
        Label testLabel = new Label("Options Pane");
        HBox.setMargin(testLabel, MainUI.PADDING);
        Button testButton = new Button("Test Button");
        testButton.setOnAction(e-> System.out.println("test button pressed") );

        optionsPane.getChildren().addAll(testLabel, testButton);
        optionsPane.setAlignment(Pos.CENTER);
        optionsPane.setPadding(MainUI.PADDING);

        mainUI.setOptionsPane(optionsPane);
    }

}
