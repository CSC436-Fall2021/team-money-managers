package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.MainUI.ServicesView;
import javafx.scene.layout.Pane;

public abstract class TransactionChart {

    protected String title;
    protected ChartData data;

    public String getTitle() {
        return title;
    }

    public ChartData getData() {
        return data;
    }

    public abstract Pane getView();
}
