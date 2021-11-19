package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Shows scatter chart view of different category spending over time.
 */
public class ScatterView extends TransactionChart {

    private ScatterChart<Long, Double> chart;
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

        List<XYChart.Series<Long, Double>> categorySeries = new ArrayList<>();
        List<Long> dates = new ArrayList<>();

        // construct XYChart.Data objects
        long minDate = Long.MAX_VALUE;
        long maxDate = Long.MIN_VALUE;

        double min = 0.00;
        double max = Double.MIN_VALUE;
        for (String category : categoryNames) {
            List<XYChart.Data<Long, Double>> entries = new ArrayList<>();

            // TODO: look over this now that no more income/expense types. Might be better way to do this.
            List<Transaction> categoryTransactions = data.getTransactionsCategory(category);

            for (Transaction transaction: categoryTransactions) {
                double amount = transaction.getAmount();
                long dateNum = transaction.getDateAsLong();

                XYChart.Data<Long, Double> entry = new XYChart.Data<Long, Double>(dateNum, amount);
                entries.add(entry);
                dates.add(dateNum);

                // update lower and upper bounds for scatter chart
                /*if (amount < min) {
                    min = amount;
                }*/

                if (amount > max) {
                    max = amount;
                }

                if (dateNum > maxDate) {
                    maxDate = dateNum;
                }

                if (dateNum < minDate) {
                    minDate = dateNum;
                }
            }

            XYChart.Series<Long, Double> series =
                    new XYChart.Series<Long, Double>(category, FXCollections.observableArrayList(entries));

            categorySeries.add(series);
        }

        // set max to be a little more than needed so chart looks better
        max *= 1.2;

        // move min and max dates by small number so end points are not cut in half.
        //minDate -= 1;
        //maxDate += 1;
        // Construct needed objects for Java's ScatterChart.
        NumberAxis xAxis = new NumberAxis(minDate, maxDate, 10);
        Axis yAxis = new NumberAxis(min, max, 10);

        // convert xAxis date numbers into string dates.
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                long val = number.longValue();

                LocalDate date = Transaction.getDateFromLong(val);
                return date.toString();
            }

            @Override
            public Number fromString(String s) {
                LocalDate date = LocalDate.parse(s);
                return date.toEpochDay();
            }
        });

        chart = new ScatterChart<Long, Double>((Axis)xAxis, yAxis);
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
