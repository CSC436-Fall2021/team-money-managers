package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Provides useful functions for getting data about a list of Transactions
 *  for the purpose of displaying graphical information in a chart.
 *
 *  Named ChartData because it was meant to provide methods to act on a chart's data,
 *  but it can be used anywhere where you are acting on a list of Transactions by category.
 *
 */
public class ChartData {

    /*
    *
    * Useful restriction options for transactions and sums:
    *
    *   1. All-time (default)
    *   2. Past week
    *   3. Past month
    *   4. This month
    *
    *
     */

    private Map<String, List<Transaction>> allCategoryTransactions;
    private Map<String, List<Transaction>> timeframeTransactions;
    private Map<String, Double> timeframeSums;

    public ChartData(List<Transaction> transactions) {

        allCategoryTransactions = new HashMap<>();
        timeframeTransactions = allCategoryTransactions;

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();


            if (!allCategoryTransactions.containsKey(category)) {
                allCategoryTransactions.put(category, new ArrayList<Transaction>());
            }

            allCategoryTransactions.get(category).add(transaction);

        }

        timeframeSums = new HashMap<>();
        updateSums();
    }

    /**
     * Returns the list of all transactions by category type within set timeframe.
     * @param category
     * @return list of all transactions by category type within set timeframe.
     */
    public List<Transaction> getTransactionsCategory(String category) {
        if (!timeframeTransactions.containsKey(category)) {
            return new ArrayList<Transaction>(); // no null check needed in caller
        }

        return timeframeTransactions.get(category);
    }

    /**
     * Returns the list of all transactions by category type within set timeframe.
     *
     * Sorted on either:
     * 1. transaction's date
     * 2. transaction's amount
     * 3. transaction's category
     *
     * @param category
     * @return list of all sorted transactions of a category type within set timeframe.
     */
    public List<Transaction> getTransactionsCategory(String category, ChartSort sortType) {
        if (!timeframeTransactions.containsKey(category)) {
            return new ArrayList<>(); // no null check needed in caller
        }

        List<Transaction> transactions = timeframeTransactions.get(category);

        if (sortType == ChartSort.DATE) {
            transactions.sort(Comparator.comparingInt(Transaction::getDateAsInt));
        } else if (sortType == ChartSort.AMOUNT) {
            transactions.sort(Comparator.comparingDouble(Transaction::getAmount));
        } else if (sortType == ChartSort.CATEGORY) {
            transactions.sort(Comparator.comparing(Transaction::getCategory));
        }

        return transactions;

    }

    /**
     * Returns the list of all transactions by category type made in the current month.
     * @param category
     * @return list of all transactions by category type made in the current month.
     */
    private List<Transaction> getTransactionsCategoryThisMonth(String category) {
        List<Transaction> transactions = allCategoryTransactions.get(category);
        List<Transaction> inTimeframe = new ArrayList<>();

        LocalDate curDate = LocalDate.now();
        for (Transaction transaction : transactions) {
            LocalDate otherDate = transaction.getDate();

            if (curDate.getMonth() == otherDate.getMonth() ) { // may need .equals()
                inTimeframe.add(transaction);
            }
        }

        return inTimeframe;
    }

    /**
     * Returns the list of all transactions by category type made within a given number of days.
     * @param category
     * @param days
     * @return list of all transactions by category type made within a given number of days.
     */
    private List<Transaction> getTransactionsPastDays(String category, int days) {
        List<Transaction> transactions = allCategoryTransactions.get(category);
        List<Transaction> inTimeframe = new ArrayList<>();

        LocalDate curDate = LocalDate.now();
        for (Transaction transaction : transactions) {
            LocalDate otherDate = transaction.getDate();

            if (ChronoUnit.DAYS.between(otherDate, curDate) <= days) {
                inTimeframe.add(transaction);
            }
        }

        return inTimeframe;
    }


    /**
     * Returns the net sum of transactions of a category type within set timeframe.
     * @param category
     * @return net sum of transactions of a category within set timeframe.
     */
    public double getNetCategory(String category) {
        return timeframeSums.get(category);
    }




    /**
     * Returns the set of categories of the transactions within set timeframe.
     *
     @return set of categories.
     */
    public Set<String> getCategorySet() {
        return timeframeSums.keySet();
    }

    /**
     * Returns whether there is any data within set timeframe to display.
     *
     * @return true if at least one transaction data is in the set timeframe, false otherwise.
     */
    public boolean hasData() {
        return !timeframeTransactions.isEmpty();
    }

    /**
     * Sets a restriction on transactions to be within a given timeframe.
     *
     * After calling this method:
     *
     * 1. getTransactionsCategory will return only transactions of a category type within the passed in timeframe restriction.
     *
     * 2. getNetCategory will return the net sum of only the transactions within the given timeframe.
     *
     * 3. getCategorySet will return only the category types that appear in transactions within the timeframe.
     *
     * @param timeframe
     */
    public void updateTimeframe(ChartTimeframe timeframe) {
        if (timeframe == ChartTimeframe.ALL) {
            timeframeTransactions = allCategoryTransactions;
            updateSums();
            return;
        }

        timeframeTransactions = new HashMap<>();

        for (String category : allCategoryTransactions.keySet()) {
            List<Transaction> inTimeframe = null;

            if (timeframe == ChartTimeframe.PAST_MONTH) {
                inTimeframe = getTransactionsPastDays(category, 30);
            } else if (timeframe == ChartTimeframe.PAST_WEEK) {
                inTimeframe = getTransactionsPastDays(category, 7);
            } else if (timeframe == ChartTimeframe.THIS_MONTH) {
                inTimeframe = getTransactionsCategoryThisMonth(category);
            }

            timeframeTransactions.put(category, inTimeframe); // inTimeframe will never be null.
        }

        timeframeSums = new HashMap<>();
        updateSums();
    }

    /**
     * Updates the sums of the categories within a timeframe.
     */
    private void updateSums() {
        for (String category : timeframeTransactions.keySet()) {
            sumCategory(category);
        }
    }

    /**
     * Sets the sum for a category of transactions within a timeframe.
     * @param category
     */
    private void sumCategory(String category) {
        double sum = 0.0;

        for (Transaction transaction : timeframeTransactions.get(category)) {
            sum += transaction.getAmount();
        }

        timeframeSums.put(category, sum);
    }


}
