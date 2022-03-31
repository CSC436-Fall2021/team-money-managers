package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Charts.TransactionChart;

/**
 * This class represents the chart UI display for the services pane.
 */
public class ChartUI extends ServicesView {

    private final TransactionChart chartView;

    /**
     * Constructor.
     *
     * @param chart the Chart object to display.
     */
    public ChartUI(TransactionChart chart) {
        super(chart.getTitle(), "Return");
        this.chartView = chart;
        this.setCenter(chartView.getView());

    }

    @Override
    void initContent() {
        // add any non-chart related UI elements
    }
}
