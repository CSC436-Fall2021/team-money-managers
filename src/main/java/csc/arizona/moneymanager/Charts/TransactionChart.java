package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.MainUI.ServicesView;
import javafx.scene.chart.Chart;
import javafx.scene.layout.Pane;

public abstract class TransactionChart {

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
