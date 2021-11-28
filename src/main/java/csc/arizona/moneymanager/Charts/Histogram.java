package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Shows a histogram which has category names for the x-axis
 *  and net income-expense sums for the y-axis.
 */
public class Histogram extends TransactionChart {

    public Histogram(List<Transaction> transactions) {
        super(transactions);
        title = "Transactions by Category: Histogram";

        recreateChart();

    }

    @Override
    protected void recreateChart() {
        if (!data.hasData()) {
            mainChart = null;
            return;
        }

        Set<String> categoryNames = data.getCategorySet();

        List<XYChart.Series<String, Double>> allSeries = new ArrayList<>();

        // construct XYChart.Data objects
        double min = 0.00;
        double max = Double.MIN_VALUE;
        for (String category : categoryNames) {
            double sum = data.getNetCategory(category);
            XYChart.Data<String, Double> entry = new XYChart.Data<String, Double>(category, sum);

            XYChart.Series<String, Double> series = new XYChart.Series<>(category,
                    FXCollections.observableArrayList(entry));

            allSeries.add(series);

            // update lower and upper bounds for bar chart

            if (sum > max) {
                max = sum;
            }

            // maybe update axis tick.
        }

        // set max to be a little more than needed so chart looks better
        max *= 1.2;

        // Construct needed objects for Java's BarChart.
        //ObservableList<XYChart.Series<String, Double>> barObservable = FXCollections.observableArrayList(allSeries);

        Axis xAxis = new CategoryAxis(FXCollections.observableArrayList(categoryNames));
        Axis yAxis = new NumberAxis(min, max, 10);

        mainChart = new StackedBarChart<String, Double>(xAxis, yAxis);
        mainChart.setAnimated(false);
        ((StackedBarChart)mainChart).getData().addAll(allSeries);
    }


}
