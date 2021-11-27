package csc.arizona.moneymanager.Charts;

import javafx.scene.chart.Chart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public abstract class TransactionChart {

    protected static final Label MISSING_DATA_LABEL = new Label("No Transaction data available.");

    protected String title;
    protected ChartData data;
    protected Chart mainChart;
    protected Pane additionalInfo;

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
            pane.setRight(additionalInfo);
        } else {
            pane.setCenter(MISSING_DATA_LABEL);
        }

        return pane;
    }}
