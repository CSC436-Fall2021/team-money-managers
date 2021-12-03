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
    protected static final ComboBox<String> timeframeTypeDropdown =
            new ComboBox<String>(FXCollections.observableArrayList(Arrays.asList("All time",
                    "Past week",
                    "Past month",
                    "This month",
                    "Custom")));

    protected static final DatePicker dateSelect1 = new DatePicker();
    protected static final DatePicker dateSelect2 = new DatePicker();
    private Label dateLabel1;
    private Label dateLabel2;

    public TransactionChart(List<Transaction> transactions) {
        viewPane = new BorderPane();
        data = new ChartData(transactions);
        additionalInfo = new BorderPane();


        GridPane timeframeSettings = new GridPane();
        timeframeSettings.setAlignment(Pos.CENTER);

        // Top section (2 rows, centered)
        timeframeSettings.add(new Label("Duration"), 1, 0);
        timeframeSettings.add(timeframeTypeDropdown, 1, 1);

        // date selectors set to the sides of the timeframe dropdown.
        dateLabel1 = new Label("Start");
        dateLabel2 = new Label("End");
        timeframeSettings.add(dateLabel1, 0, 0);
        timeframeSettings.add(dateSelect1, 0, 1);
        timeframeSettings.add(dateLabel2, 2, 0);
        timeframeSettings.add(dateSelect2, 2, 1);

        /*
        * On creation, restores the timeframe settings last set by the user during program runtime.
        *
        * When the user wants to view a different chart, or set a different budget, etc.
        * their timeframe won't be reset back to "All time"
        *
         */

        String prevTimeSelection = timeframeTypeDropdown.getValue();
        if (prevTimeSelection == null) { // first chart to be created. setup all long-term static objects.
            timeframeTypeDropdown.setEditable(false);
            timeframeTypeDropdown.getSelectionModel().selectFirst(); // select a default ("All time")

            // set all date items to be invisible on start (default = "All time")
            dateLabel1.setVisible(false);
            dateLabel2.setVisible(false);
            dateSelect1.setVisible(false);
            dateSelect2.setVisible(false);

            // disable editing datepickers
            dateSelect1.setEditable(false);
            dateSelect2.setEditable(false);
        } else if (prevTimeSelection.equals("Custom")) {
            // if date selectors were set, then restore custom timeframe, else, defaults to "all time"
            if (checkCustomUpdate()) {
                LocalDate start = dateSelect1.getValue();
                LocalDate end = dateSelect2.getValue();
                data.updateTimeframe(start, end);
            }
        } else if (!prevTimeSelection.equals("All time")) { // any timeframe not all time, restore timeframe
            data.updateTimeframe(prevTimeSelection);
        } else { // prevTimeSelection == "All time"
            // all time, this is the default, so do nothing.
        }



        /*
        * Event handlers have to be set on every chart creation.
        * They can't be set only on first chart creation.
        *
        * This is because the recreateChart(this) call needs to be updated to point to the new chart.
        *
         */

        // if user selects "custom", show date pickers. otherwise, hide them.
        timeframeTypeDropdown.setOnAction(e -> {

            String selectedType = timeframeTypeDropdown.getValue();

            if (selectedType.equals("Custom")) {
                dateLabel1.setVisible(true);
                dateLabel2.setVisible(true);
                dateSelect1.setVisible(true);
                dateSelect2.setVisible(true);
                if (checkCustomUpdate()) {
                    recreateChart();
                }
            } else {
                dateLabel1.setVisible(false);
                dateLabel2.setVisible(false);
                dateSelect1.setVisible(false);
                dateSelect2.setVisible(false);
                data.updateTimeframe(selectedType);
                recreateChart();
            }


        });

        // for both datepickers, recreate the chart after user selects a date - if both dates have been set.
        dateSelect1.setOnAction(e -> {
            if (checkCustomUpdate()) {
                recreateChart();
            }
        });

        dateSelect2.setOnAction(e -> {
            if (checkCustomUpdate()) {
                recreateChart();
            }
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

    /**
     * Implemented by subclasses.
     *
     * Do whatever you need to do to reflect the changes to the timeframe onto the chart.
     */
    protected abstract void recreateChart();

    /**
     * Updates the pane as seen in the Service view.
     *
     * The pane will always display a timeframe selection at the bottom of the pane (set in constructor).
     *
     * If the timeframe has any transaction data within it, display the chart created from that data.
     * Otherwise, display that there is no data available with the given settings.
     */
    protected void updatePane() {
        if (data.hasData()) {
            viewPane.setCenter(mainChart);
        } else {
            viewPane.setCenter(MISSING_DATA_LABEL);
        }
    }

    /**
     * For the custom timeframe selection,
     * returns whether the data timeframe should be updated and the chart recreated.
     *
     * @return true if both date selectors have been set, otherwise false.
     */
    private boolean checkCustomUpdate() {
        LocalDate startDate = dateSelect1.getValue();
        LocalDate endDate = dateSelect2.getValue();

        if (startDate != null && endDate != null) {
            data.updateTimeframe(startDate, endDate);
            return true;
        }

        return false;

    }

}
