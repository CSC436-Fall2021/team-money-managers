package csc.arizona.moneymanager.MainUI;

import javafx.scene.chart.Chart;

/**
 * This class represents the chart UI display for the services pane.
 *
 * //TODO possibly add the underlying Observable list to left as a listed version of the items in the chart
 */
public class ChartUI extends ServicesView{

    private Chart chart;
    private final double CHART_SCALE = 1.0;

    /**
     * Constructor.
     *
     * @param chart the Chart object to display.
     */
    public ChartUI(Chart chart) {
        super(chart.getTitle(), "Return" );
        this.chart = chart;

        setupChart();

        this.setCenter(chart);

    }

    @Override
    void initContent() {
        //TODO add any non-chart related UI elements
    }

    /**
     * Setup related to the chart.
     */
    private void setupChart(){
        chart.setTitle(""); // Clearing title as it was set to main title of ChartUI
        chart.setScaleX(CHART_SCALE);
        chart.setScaleY(CHART_SCALE);
    }

}
