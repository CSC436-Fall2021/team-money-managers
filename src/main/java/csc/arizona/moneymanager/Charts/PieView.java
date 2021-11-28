package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * Shows two pie charts for incomes and expenses by category type.
 */
public class PieView extends TransactionChart {

    public PieView(List<Transaction> transactions) {
        super(transactions);

        title = "Transactions by Category: PieChart";
        recreateChart();
    }


    @Override
    protected void recreateChart() {
        if (!data.hasData()) {
            mainChart = null;
            return;
        }

        Set<String> categoryNames = data.getCategorySet();

        List<PieChart.Data> pieCategoryTotals = new ArrayList<>(); // JavaFX pi data for for income data pi chart

        // set up PieChart.Data objects for Java's PieChart
        for (String category : categoryNames) {

            double categoryIncome = data.getNetCategory(category);

            // all strings in categoryNames are valid from ChartData.getCategorySet().
            // so, no need to check.
            PieChart.Data pieDataEntry = new PieChart.Data(category, categoryIncome);
            pieCategoryTotals.add(pieDataEntry);

        }

        // set up actual PieChart using the PieChart.Data objects we created
        ObservableList<PieChart.Data> pieDataObservable = FXCollections.observableArrayList(pieCategoryTotals);

        mainChart = new PieChart(pieDataObservable);
    }
}
