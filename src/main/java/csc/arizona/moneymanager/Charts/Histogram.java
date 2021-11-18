package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Shows a histogram which has category names for the x-axis
 *  and net income-expense sums for the y-axis.
 */
public class Histogram extends TransactionChart {

    private BarChart<String, Double> chart;

    public Histogram(List<Transaction> transactions) {
        title = "Transactions by Category: Histogram";
        data = new ChartData(transactions);

        Set<String> categoryNames = data.getCategorySet();

        List<XYChart.Data<String, Double>> entries = new ArrayList<>();

        // construct XYChart.Data objects
        double min = 0.00;
        double max = Double.MIN_VALUE;
        for (String category : categoryNames) {
            double sum = data.getNetCategory(category);

            XYChart.Data<String, Double> entry = new XYChart.Data<String, Double>(category, sum);

            entries.add(entry);

            // update lower and upper bounds for bar chart
            /*if (sum < min) {
                min = sum;
            }*/

            if (sum > max) {
                max = sum;
            }

            // maybe update axis tick.
        }

        // TODO: if no transactions, update max if error

        // Construct needed objects for Java's BarChart.
        XYChart.Series<String, Double> series = new XYChart.Series<String, Double>(FXCollections.observableArrayList(entries));
        ObservableList<XYChart.Series<String, Double>> barObservable = FXCollections.observableArrayList(series);

        Axis xAxis = new CategoryAxis(FXCollections.observableArrayList(categoryNames));
        Axis yAxis = new NumberAxis(min, max, 10);

        chart = new BarChart<String, Double>(xAxis, yAxis, barObservable);

        //display
    }

    @Override
    public Pane getView() {
        BorderPane pane = new BorderPane();

        if (data.hasData()) {
            pane.setCenter(chart);
        } else {
            pane.setCenter(MISSING_DATA_LABEL);
        }

        return pane;
    }
}
