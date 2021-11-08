package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Histogram extends Chart {

    public Histogram(List<Transaction> transactions) {
        data = new ChartData(transactions);

        Set<String> categoryNames = data.getCategorySet();

        List<XYChart.Data<String, Double>> entries = new ArrayList<>();

        for (String category : categoryNames) {
            double sum = data.getNetCategory(category);

            XYChart.Data<String, Double> entry = new XYChart.Data<String, Double>(category, sum);

            entries.add(entry);
        }

        XYChart.Series<String, Double> series = new XYChart.Series<String, Double>(FXCollections.observableArrayList(entries));
        ObservableList<XYChart.Series<String, Double>> barObservable = FXCollections.observableArrayList(series);

        Axis xAxis = new CategoryAxis(); // TODO observableList
        Axis yAxis = new NumberAxis(0, 500, 10);

        BarChart<String, Double> chart = new BarChart<String, Double>(xAxis, yAxis, barObservable);

        //display
    }

}
