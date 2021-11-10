package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Charts.TransactionChart;
import javafx.scene.chart.Chart;

/**
 * This class represents the chart UI display for the services pane.
 *
 * //TODO possibly add the underlying Observable list to left as a listed version of the items in the chart
 */
public class ChartUI extends ServicesView{

    private TransactionChart chartView;
    private final double CHART_SCALE = 1.0;

    /**
     * Constructor.
     *
     * @param chart the Chart object to display.
     */
    public ChartUI(TransactionChart chart) {
        super(chart.getTitle(), "Return" );
        this.chartView = chart;

        //setupChart();

        this.setCenter(chartView.getView());

    }

    @Override
    void initContent() {
        //TODO add any non-chart related UI elements
    }

    /**
     * Setup related to the chart.
     */
    private void setupChart(){
        //chart.setTitle(""); // Clearing title as it was set to main title of ChartUI
        //chart.setScaleX(CHART_SCALE);
        //chart.setScaleY(CHART_SCALE);
    }

}
