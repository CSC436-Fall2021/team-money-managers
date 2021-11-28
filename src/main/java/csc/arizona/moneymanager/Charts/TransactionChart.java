package csc.arizona.moneymanager.Charts;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class TransactionChart {

    protected static final Label MISSING_DATA_LABEL = new Label("No Transaction data available.");

    protected String title;
    protected ChartData data;
    protected Chart mainChart;
    protected BorderPane additionalInfo;
    protected ComboBox<String> timeframeTypeDropdown;
    protected DatePicker dateSelect1;
    protected DatePicker dateSelect2;
    private Label dateLabel1;
    private Label dateLabel2;

    public TransactionChart() {
        List<String> timeOptions = Arrays.asList("All time",
                "Past week",
                "Past month",
                "This month",
                "Custom");
        additionalInfo = new BorderPane();

        timeframeTypeDropdown = new ComboBox<String>(FXCollections.observableArrayList(timeOptions));
        timeframeTypeDropdown.setEditable(false);
        timeframeTypeDropdown.getSelectionModel().selectFirst(); // select a default ("All time")

        dateSelect1 = new DatePicker();
        dateSelect2 = new DatePicker();
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

            if (selectedType.equals("Custom")) {
                dateLabel1.setVisible(true);
                dateLabel2.setVisible(true);
                dateSelect1.setVisible(true);
                dateSelect2.setVisible(true);
            } else {
                dateLabel1.setVisible(false);
                dateLabel2.setVisible(false);
                dateSelect1.setVisible(false);
                dateSelect2.setVisible(false);
            }

        });




        additionalInfo.setCenter(timeframeSettings);
        //additionalInfo.setCenter(MISSING_DATA_LABEL);
    }

    public String getTitle() {
        return title;
    }

    public ChartData getData() {
        return data;
    }

    public Pane getView() {
        BorderPane pane = new BorderPane();

        if (data.hasData()) {
            pane.setCenter(mainChart);
            pane.setBottom(additionalInfo);
        } else {
            pane.setCenter(MISSING_DATA_LABEL);
        }

        return pane;
    }

}
