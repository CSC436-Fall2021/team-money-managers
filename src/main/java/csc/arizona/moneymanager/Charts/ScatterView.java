package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.*;

/**
 * Shows scatter chart view of different category spending over time.
 */
public class ScatterView extends TransactionChart {

    private LineChart<Long, Double> chart;
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

        // get start and end dates of the list of transactions
        long minDate = data.getStartDate().toEpochDay();
        long maxDate = data.getEndDate().toEpochDay();

        // create budget line as first series
        List<XYChart.Data<Long, Double>> budgetEntries = new ArrayList<>();

        XYChart.Data<Long, Double> budget1 = new XYChart.Data<>(minDate, budget);
        XYChart.Data<Long, Double> budget2 = new XYChart.Data<>(maxDate, budget);
        budgetEntries.add(budget1);
        budgetEntries.add(budget2);

        XYChart.Series<Long, Double> budgetSeries =
                new XYChart.Series<Long, Double>("Budget", FXCollections.observableArrayList(budgetEntries));

        categorySeries.add(budgetSeries);

        double min = 0.00;
        double max = budget;

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

                // update lower and upper bounds for scatter chart

                if (amount > max) {
                    max = amount;
                }

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
        max = (int)(max*1.1);

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

        chart = new LineChart<Long, Double>((Axis)xAxis, yAxis);
        chart.setCreateSymbols(true);
        chart.getData().addAll(categorySeries);

        // set budget to always be red and total to always be purple
        String budgetColor = "red";
        String totalColor = "purple";
        int totalIndex = categorySeries.size() - 1;

        Node budgetLineNode = chart.lookup(".default-color0.chart-series-line");
        Node budgetSymbolNode = chart.lookup(".default-color0.chart-line-symbol");

        budgetLineNode.setStyle("-fx-stroke: " + budgetColor);
        // TODO: why does this do nothing? why does it only affect the first node
        budgetSymbolNode.setStyle("-fx-background-color: transparent, transparent"); // remove budget's symbol

        Node totalLineNode = chart.lookup(".default-color" + totalIndex + ".chart-series-line");
        Node totalSymbolNode = chart.lookup(".default-color" + totalIndex + ".chart-line-symbol");

        totalLineNode.setStyle("-fx-stroke: " + totalColor);
        // TODO: why does this do nothing? why does it only affect the first node
        totalSymbolNode.setStyle("-fx-background-color: " + totalColor + ", " + totalColor);


        // remove lines for all series except budget and total
        for (int i = 1; i < categorySeries.size()-1; i++) {
            String element = ".default-color" + i + ".chart-series-line";
            Node node = chart.lookup(element);
            node.setStyle("-fx-stroke: transparent");

        }

        //display
    }

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
