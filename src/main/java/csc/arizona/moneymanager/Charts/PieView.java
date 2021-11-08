package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.*;

public class PieView extends Chart {

    public PieView(List<Transaction> transactions) {
        data = new ChartData(transactions);

        Set<String> categoryNames = data.getCategorySet();

        List<PieChart.Data> piDataIncome = new ArrayList<>(); // pi data for for income data pi chart
        List<PieChart.Data> piDataExpense = new ArrayList<>(); // pi data for expense data pi chart



        ObservableList<PieChart.Data> piIncomeObservable = FXCollections.observableArrayList(piDataIncome);
        ObservableList<PieChart.Data> piExpenseObservable = FXCollections.observableArrayList(piDataExpense);

        PieChart incomeChart = new PieChart(piIncomeObservable);
        PieChart expenseChart = new PieChart(piExpenseObservable);

    }
}
