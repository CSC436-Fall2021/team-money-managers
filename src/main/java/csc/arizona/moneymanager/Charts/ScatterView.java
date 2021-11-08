package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScatterView extends Chart {

    public ScatterView(List<Transaction> transactions) {
        data = new ChartData(transactions);

        Set<String> categoryNames = data.getCategorySet();

        List<XYChart.Series<String, Double>> categorySeries = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        // construct XYChart.Data objects
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (String category : categoryNames) {
            List<XYChart.Data<String, Double>> entries = new ArrayList<>();

            List<Transaction> incomes = data.getIncomesCategory(category);
            List<Transaction> expenses = data.getExpensesCategory(category);

            for (Transaction transaction: incomes) {
                double amount = transaction.getAmount();
                LocalDate date = transaction.getDate();
                XYChart.Data<String, Double> entry = new XYChart.Data<String, Double>(date.toString(), amount);
                entries.add(entry);
                dates.add(date.toString());

                // update lower and upper bounds for scatter chart
                if (amount < min) {
                    min = amount;
                }

                if (amount > max) {
                    max = amount;
                }
            }

            for (Transaction transaction: expenses) {
                double amount = transaction.getAmount();
                LocalDate date = transaction.getDate();
                XYChart.Data<String, Double> entry = new XYChart.Data<String, Double>(date.toString(), amount);
                entries.add(entry);
                dates.add(date.toString());

                // update lower and upper bounds for scatter chart
                if (amount < min) {
                    min = amount;
                }

                if (amount > max) {
                    max = amount;
                }
            }

            XYChart.Series<String, Double> series =
                    new XYChart.Series<String, Double>(category, FXCollections.observableArrayList(entries));

            categorySeries.add(series);
        }

        // Construct needed objects for Java's ScatterChart.
        Axis xAxis = new CategoryAxis(FXCollections.observableArrayList(dates));
        Axis yAxis = new NumberAxis(min, max, 10);

        ScatterChart<String, Double> chart = new ScatterChart<String, Double>(xAxis, yAxis);
        chart.getData().addAll(categorySeries);

        //display
    }

}
