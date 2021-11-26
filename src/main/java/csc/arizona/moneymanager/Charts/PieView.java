package csc.arizona.moneymanager.Charts;
//
import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Shows two pie charts for incomes and expenses by category type.
 */
public class PieView extends TransactionChart {

    PieChart transactionsByCategoryChart;

    public PieView(List<Transaction> transactions) {
        title = "Transactions by Category: PieChart";
        data = new ChartData(transactions);

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

        transactionsByCategoryChart = new PieChart(pieDataObservable);

        // display
    }

    @Override
    public Pane getView() {
        BorderPane pane = new BorderPane();


        if (data.hasData()) {
            pane.setCenter(transactionsByCategoryChart);
        } else {
            pane.setCenter(MISSING_DATA_LABEL);
        }

        return pane;
    }
}
