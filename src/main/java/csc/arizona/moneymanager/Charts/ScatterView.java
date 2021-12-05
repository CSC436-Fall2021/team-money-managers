package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.Controller;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.*;

/**
 * Shows scatter chart view of different category spending over time.
 */
public class ScatterView extends TransactionChart {

    private double budget;
    private String budgetDuration;

    public ScatterView(List<Transaction> transactions, double budget, String budgetDuration) {
        super(transactions);
        title = "Transactions by category over time: Scatter";
        this.budget = budget;
        this.budgetDuration = budgetDuration;

        recreateChart();

    }

    /**
     * Gets the nearest number to the original number that is divisible by divisibleBy.
     * Useful for graph ticks. May be useful for other charts as well (may refactor).
     *
     * ex:
     * getNearest(38, 5) returns 40
     * getNearest(36, 5) returns 35
     *
     * @param original
     * @param divisibleBy
     * @return
     */
    private int getNearest(int original, int divisibleBy) {
        int nearest = original;
        int rem = original % divisibleBy;

        if (rem > divisibleBy / 2) { // if closer to the next number divisible by x
            nearest += (divisibleBy - rem);
        } else { // if closer to the previous number divisible by x
            nearest -= rem;
        }
        return nearest;
    }

    @Override
    protected void recreateChart() {
        if (!data.hasData()) {
            mainChart = null;
            updatePane();
            return;
        }

        Set<String> categoryNames = data.getCategorySet();

        List<XYChart.Series<Long, Double>> categorySeries = new ArrayList<>();
        List<Long> dates = new ArrayList<>();

        // construct XYChart.Data objects

        // get start and end dates of the list of transactions
        long minDate = data.getStartDate().toEpochDay();
        long maxDate = data.getEndDate().toEpochDay();


        // create budget line as first series if chart timeframe and budget duration makes sense together.
        if (shouldDisplayBudget()) {
            List<XYChart.Data<Long, Double>> budgetEntries = new ArrayList<>();

            XYChart.Data<Long, Double> budget1 = new XYChart.Data<>(minDate, budget);
            XYChart.Data<Long, Double> budget2 = new XYChart.Data<>(maxDate, budget);
            budgetEntries.add(budget1);
            budgetEntries.add(budget2);

            XYChart.Series<Long, Double> budgetSeries =
                    new XYChart.Series<Long, Double>("Budget", FXCollections.observableArrayList(budgetEntries));

            categorySeries.add(budgetSeries);
        }

        double min = 0.00;
        double max = 0.00;
        if (shouldDisplayBudget()) {
            max = budget;
        }

        Map<Long, Double> dateSums = new HashMap<Long, Double>();
        for (String category : categoryNames) {
            List<XYChart.Data<Long, Double>> entries = new ArrayList<>();

            // TODO: look over this now that no more income/expense types. Might be better way to do this.
            List<Transaction> categoryTransactions = data.getTransactionsCategory(category);

            for (Transaction transaction : categoryTransactions) {
                double amount = transaction.getAmount();
                long dateNum = transaction.getDateAsLong();

                XYChart.Data<Long, Double> entry = new XYChart.Data<Long, Double>(dateNum, amount);
                entries.add(entry);
                dates.add(dateNum);

                // sum the total for dates (to be used later in generating a 'total' line)
                // this could be made into a method in ChartData 'getNetSinceStart'
                if (!dateSums.containsKey(dateNum)) {
                    dateSums.put(dateNum, amount);
                } else {
                    double prevSum = dateSums.get(dateNum);
                    dateSums.put(dateNum, prevSum + amount);
                }


            }

            XYChart.Series<Long, Double> series =
                    new XYChart.Series<Long, Double>(category, FXCollections.observableArrayList(entries));

            categorySeries.add(series);
        }

        // create 'total' data points for dates
        double total = 0.0;
        List<XYChart.Data<Long, Double>> entries = new ArrayList<>();
        Set<Long> sortedKeys = new TreeSet<Long>(dateSums.keySet());
        for (long dateNum : sortedKeys) {
            double amount = dateSums.get(dateNum);

            total += amount;

            XYChart.Data<Long, Double> entry = new XYChart.Data<Long, Double>(dateNum, total);

            entries.add(entry);

            if (total > max) {
                max = total;
            }

        }

        XYChart.Series<Long, Double> totalSeries =
                new XYChart.Series<Long, Double>("total", FXCollections.observableArrayList(entries));

        categorySeries.add(totalSeries);


        // set max to be a little more than needed so chart looks better
        max = (int)(max*1.1) + 5/2;
        max = getNearest((int)max, 5); // move max to nearest num div by 5

        // move min and max dates by small number so end points are not cut in half.
        //minDate -= 1;
        //maxDate += 1;

        // Construct needed objects for Java's ScatterChart.
        // set spending tick to be a portion of the highest series, to the nearest divisible by 5.
        int yAxisTick = (int)(max / 20);
        yAxisTick = getNearest(yAxisTick, 5);

        NumberAxis xAxis = new NumberAxis(minDate, maxDate, 1);
        Axis yAxis = new NumberAxis(min, max, yAxisTick);

        // convert xAxis date numbers into string dates.
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                long val = number.longValue();

                LocalDate date = LocalDate.ofEpochDay(val);
                return date.toString();
            }

            @Override
            public Number fromString(String s) {
                LocalDate date = LocalDate.parse(s);
                return date.toEpochDay();
            }
        });

        mainChart = new LineChart<Long, Double>((Axis)xAxis, yAxis);
        ((LineChart)mainChart).setCreateSymbols(true);
        ((LineChart)mainChart).getData().addAll(categorySeries);

        // set budget to always be red, if displayed
        if (shouldDisplayBudget()) {
            String budgetColor = "red";

            Node budgetLineNode = mainChart.lookup(".default-color0.chart-series-line");
            Node budgetSymbolNode = mainChart.lookup(".default-color0.chart-line-symbol");

            budgetLineNode.setStyle("-fx-stroke: " + budgetColor);
            // TODO: why does this do nothing? why does it only affect the first node
            budgetSymbolNode.setStyle("-fx-background-color: transparent, transparent"); // remove budget's symbol
        }

        // set total to always be purple
        String totalColor = "purple";
        int totalIndex = categorySeries.size() - 1;
        Node totalLineNode = mainChart.lookup(".default-color" + totalIndex + ".chart-series-line");
        Node totalSymbolNode = mainChart.lookup(".default-color" + totalIndex + ".chart-line-symbol");

        totalLineNode.setStyle("-fx-stroke: " + totalColor);
        // TODO: why does this do nothing? why does it only affect the first node
        totalSymbolNode.setStyle("-fx-background-color: " + totalColor + ", " + totalColor);


        // remove lines for all series except budget and total
        if (shouldDisplayBudget()) {
            for (int i = 1; i < categorySeries.size() - 1; i++) {
                String element = ".default-color" + i + ".chart-series-line";
                Node node = mainChart.lookup(element);
                node.setStyle("-fx-stroke: transparent");
            }

        } else {
            for (int i = 0; i < categorySeries.size() - 1; i++) {
                String element = ".default-color" + i + ".chart-series-line";
                Node node = mainChart.lookup(element);
                node.setStyle("-fx-stroke: transparent");
            }
        }

        updatePane();

    }

    /**
     * Chooses whether to display the budget in the chart or not.
     *
     * When to show budget:
     *   1. Never when timeframe == "All time".
     *   2. Never when timeframe == "Custom".
     *
     *   3. timeframe == "Past week"
     *   4. timeframe == "Past month" and budget is monthly
     *   5. timeframe == "This month" and budget is monthly
     *
     *
     *
     * @return true if budget should be displayed in the chart, otherwise false.
     */
    private boolean shouldDisplayBudget() {
        String timeframeSelection = timeframeTypeDropdown.getValue();

        // never display the budget if all time or custom
        if (timeframeSelection.equals("All time") ||
                timeframeSelection.equals("Custom")) {
            return false;
        }

        // always display the budget if past week, regardless of budget duration (weekly | monthly).
        if (timeframeSelection.equals("Past week")) {
            return true;
        }

        // display budget if past month/this month only if the budget is set to monthly.
        if (timeframeSelection.equals("Past month") || timeframeSelection.equals("This month")) {
            return budgetDuration.equals("Monthly");
        }

        // should never be reached, but return false.
        return false;
    }
}
