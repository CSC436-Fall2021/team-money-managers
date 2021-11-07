package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieView extends Chart {

    public PieView(List<Transaction> transactions) {
        List<PieChart.Data> test = new ArrayList<>();
        Map<String, Double> sums = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();

            if (!sums.containsKey(category)) {
                sums.put(category, transaction.getAmount());
            } else {
                sums.put(category, sums.get(category) + transaction.getAmount());
            }

        }

        for (String key : sums.keySet()) {
            test.add(new PieChart.Data(key, sums.get(key)));
        }
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(test);

        PieChart chart = new PieChart(data);

        //data = new ChartData(transactions);
    }
}
