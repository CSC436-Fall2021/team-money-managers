package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.chart.Chart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Base class for displaying charts in the service view.
 *
 * Sets up the space for the chart and date selection.
 *
 * Provides base methods for updating what the pane should display.
 */
public abstract class TransactionChart {

    protected static final Label MISSING_DATA_LABEL = new Label("No Transaction data available.");

    protected BorderPane viewPane;
    protected String title;
    protected ChartData data;
    protected Chart mainChart;
    protected BorderPane additionalInfo;
    protected ComboBox<String> timeframeTypeDropdown;


    /*
    * Custom date selection (from a start date to an end date).
    *
    * static = easy way to preserve custom date info across charts (user does not need to re-enter custom dates when picking new chart)
    *
    * easiest way of doing this since MainUI creates new TransactionCharts when the user wants to view them.
    *
    * an alternative way would be creating only one "ChartUI" in MainUI and having the chart select methods
    * in MainUI make ChartUI change its charts.
    *
    */
    protected static final DatePicker dateSelect1 = new DatePicker();
    protected static final DatePicker dateSelect2 = new DatePicker();
    private Label dateLabel1;
    private Label dateLabel2;

    public TransactionChart(List<Transaction> transactions) {
        viewPane = new BorderPane();
        data = new ChartData(transactions);
        List<String> timeOptions = Arrays.asList("All time",
                "Past week",
                "Past month",
                "This month",
                "Custom");
        additionalInfo = new BorderPane();

        timeframeTypeDropdown = new ComboBox<String>(FXCollections.observableArrayList(timeOptions));
        timeframeTypeDropdown.setEditable(false);
        timeframeTypeDropdown.getSelectionModel().selectFirst(); // select a default ("All time")

        dateSelect1.setEditable(false);
        dateSelect2.setEditable(false);
        // set dates to not show up immediately (default = "all time")
        //dateSelect1.setVisible(false);
        //dateSelect2.setVisible(false);


        GridPane timeframeSettings = new GridPane();
        timeframeSettings.setAlignment(Pos.CENTER);

        // Top section (2 rows, centered)
        timeframeSettings.add(new Label("Duration"), 1, 0);
        timeframeSettings.add(timeframeTypeDropdown, 1, 1);

        // dates on bottom row, to the sides
        dateLabel1 = new Label("Start");
        dateLabel2 = new Label("End");
        timeframeSettings.add(dateLabel1, 0, 0);
        timeframeSettings.add(dateSelect1, 0, 1);
        timeframeSettings.add(dateLabel2, 2, 0);
        timeframeSettings.add(dateSelect2, 2, 1);

        // set all date items to be invisible on start (default = "All time")

        dateLabel1.setVisible(false);
        dateLabel2.setVisible(false);
        dateSelect1.setVisible(false);
        dateSelect2.setVisible(false);

        // if user selects "custom", show date pickers. otherwise, hide them.
        timeframeTypeDropdown.setOnAction(e -> {

            String selectedType = timeframeTypeDropdown.getValue();
            System.out.println(selectedType);

            if (selectedType.equals("Custom")) {
                dateLabel1.setVisible(true);
                dateLabel2.setVisible(true);
                dateSelect1.setVisible(true);
                dateSelect2.setVisible(true);
                checkCustomUpdate();
            } else {
                dateLabel1.setVisible(false);
                dateLabel2.setVisible(false);
                dateSelect1.setVisible(false);
                dateSelect2.setVisible(false);
                data.updateTimeframe(selectedType);
            }

            recreateChart();


        });

        dateSelect1.setOnAction(e -> {
            checkCustomUpdate();
            recreateChart();
        });

        dateSelect2.setOnAction(e -> {
            checkCustomUpdate();
            recreateChart();
        });




        additionalInfo.setCenter(timeframeSettings);
        viewPane.setBottom(additionalInfo);
    }

    public String getTitle() {
        return title;
    }

    public ChartData getData() {
        return data;
    }

    public Pane getView() {
        return viewPane;
    }

    protected abstract void recreateChart();

    protected void updatePane() {
        if (data.hasData()) {
            viewPane.setCenter(mainChart);
        } else {
            viewPane.setCenter(MISSING_DATA_LABEL);
        }
    }

    private void checkCustomUpdate() {
        LocalDate startDate = dateSelect1.getValue();
        LocalDate endDate = dateSelect2.getValue();

        if (startDate != null && endDate != null) {
            data.updateTimeframe(startDate, endDate);
        }


    }

}
