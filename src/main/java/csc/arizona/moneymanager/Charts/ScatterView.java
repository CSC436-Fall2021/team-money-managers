package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Shows scatter chart view of different category spending over time.
 */
public class ScatterView extends TransactionChart {

    private ScatterChart<String, Double> chart;
    private double budget;

    public ScatterView(List<Transaction> transactions, double budget) {
        title = "Transactions by category over time: Scatter";
        this.budget = budget;

        data = new ChartData(transactions);

        if (!data.hasData()) {
            chart = null;
            return;
        }

        Set<String> categoryNames = data.getCategorySet();

        List<XYChart.Series<String, Double>> categorySeries = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        // construct XYChart.Data objects
        double min = 0.00;
        double max = Double.MIN_VALUE;
        for (String category : categoryNames) {
            List<XYChart.Data<String, Double>> entries = new ArrayList<>();

            // TODO: look over this now that no more income/expense types. Might be better way to do this.
            List<Transaction> categoryTransactions = data.getTransactionsCategory(category);

            for (Transaction transaction: categoryTransactions) {
                double amount = transaction.getAmount();
                LocalDate date = transaction.getDate();
                XYChart.Data<String, Double> entry = new XYChart.Data<String, Double>(date.toString(), amount);
                entries.add(entry);
                dates.add(date.toString());

                // update lower and upper bounds for scatter chart
                /*if (amount < min) {
                    min = amount;
                }*/

                if (amount > max) {
                    max = amount;
                }
            }

            XYChart.Series<String, Double> series =
                    new XYChart.Series<String, Double>(category, FXCollections.observableArrayList(entries));

            categorySeries.add(series);
        }

        // set max to be a little more than needed so chart looks better
        max *= 1.2;

        // Construct needed objects for Java's ScatterChart.
        Axis xAxis = new CategoryAxis(FXCollections.observableArrayList(dates));
        Axis yAxis = new NumberAxis(min, max, 10);

        chart = new ScatterChart<String, Double>(xAxis, yAxis);
        chart.getData().addAll(categorySeries);

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
