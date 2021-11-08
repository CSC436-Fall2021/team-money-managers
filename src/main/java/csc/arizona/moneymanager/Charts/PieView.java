package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.MainUI.ChartUI;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Shows two pie charts for incomes and expenses by category type.
 */
public class PieView extends TransactionChart {

    PieChart incomeChart;
    PieChart expenseChart;

    public PieView(List<Transaction> transactions) {
        title = "Income and Expenses by category: PieChart";
        data = new ChartData(transactions);

        Set<String> categoryNames = data.getCategorySet();

        List<PieChart.Data> pieDataIncome = new ArrayList<>(); // pi data for for income data pi chart
        List<PieChart.Data> pieDataExpense = new ArrayList<>(); // pi data for expense data pi chart

        // set up PieChart.Data objects for Java's PieChart
        for (String category : categoryNames) {
            // getGross functions return 0 if N/A for category.
            double categoryIncome = data.getGrossIncomeCategory(category);
            double categoryExpense = data.getGrossIncomeCategory(category);

            // if there was income for a category, add it to the income pie chart
            if (categoryIncome != 0) {
                PieChart.Data pieData = new PieChart.Data(category, categoryIncome);
                pieDataIncome.add(pieData);
            }

            // if there was expense for a category, add it to the expense pie chart
            if (categoryExpense != 0) {
                PieChart.Data pieData = new PieChart.Data(category, categoryExpense);
                pieDataExpense.add(pieData);
            }
        }

        // set up actual PieCharts using the PieChart.Data objects we created
        ObservableList<PieChart.Data> pieIncomeObservable = FXCollections.observableArrayList(pieDataIncome);
        ObservableList<PieChart.Data> pieExpenseObservable = FXCollections.observableArrayList(pieDataExpense);

        incomeChart = new PieChart(pieIncomeObservable);
        expenseChart = new PieChart(pieExpenseObservable);

        // display
    }

    @Override
    public Pane getView() {
        GridPane pane = new GridPane();

        pane.add(new Label("Income"), 0, 0);
        pane.add(new Label("Expense"), 1, 0);

        pane.add(incomeChart, 0, 1);
        pane.add(expenseChart, 1, 1);

        return pane;
    }
}
