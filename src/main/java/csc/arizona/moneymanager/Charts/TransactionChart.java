package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.MainUI.ServicesView;
import javafx.scene.chart.Chart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public abstract class TransactionChart {

    protected static final Label MISSING_DATA_LABEL = new Label("No Transaction data available.");

    protected String title;
    protected ChartData data;
    //protected Chart mainChart; // not in use for now.

    public String getTitle() {
        return title;
    }

    public ChartData getData() {
        return data;
    }

    public abstract Pane getView();
}
