package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Provides useful functions for getting data about a list of Transactions
 *  for the purpose of displaying graphical information in a chart.
 *
 *  Named ChartData because it was meant to provide methods to act on a chart's data,
 *  but it can be used anywhere where you are acting on a list of transactions.
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
    *   5. Custom start and end date
    *
    */

    private List<Transaction> allTransactions;
    private List<Transaction> timeframeTransactions;

    private Map<String, List<Transaction>> allCategoryTransactions;
    private Map<String, List<Transaction>> timeframeCategoryTransactions;

    private Map<String, Double> timeframeSums;
    private double timeframeSumTotal;

    public ChartData(List<Transaction> transactions) {

        allTransactions = new ArrayList<>(); // to copy user transactions
        timeframeTransactions = allTransactions;

        allCategoryTransactions = new HashMap<>();
        timeframeCategoryTransactions = allCategoryTransactions;

        for (Transaction transaction : transactions) {

            allTransactions.add(transaction);

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
     * Returns the list of all transactions within set timeframe.
     * @return list of all transactions within set timeframe.
     */
    public List<Transaction> getTransactions() {
        return timeframeTransactions;
    }

    /**
     * Returns the list of all transactions within set timeframe.
     *
     * Sorted on either:
     * 1. transaction's date
     * 2. transaction's amount
     * 3. transaction's category
     *
     * @param sortType
     * @return list of all sorted transactions within set timeframe.
     */
    public List<Transaction> getTransactions(ChartSort sortType) {

        if (sortType == ChartSort.DATE) {
            timeframeTransactions.sort(Comparator.comparingLong(Transaction::getDateAsLong));
        } else if (sortType == ChartSort.AMOUNT) {
            timeframeTransactions.sort(Comparator.comparingDouble(Transaction::getAmount));
        } else if (sortType == ChartSort.CATEGORY) {
            timeframeTransactions.sort(Comparator.comparing(Transaction::getCategory));
        }

        return timeframeTransactions;

    }

    /**
     * Returns the list of all transactions by category type within set timeframe.
     * @param category
     * @return list of all transactions by category type within set timeframe.
     */
    public List<Transaction> getTransactionsCategory(String category) {
        if (!timeframeCategoryTransactions.containsKey(category)) {
            return new ArrayList<Transaction>(); // no null check needed in caller
        }

        return timeframeCategoryTransactions.get(category);
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
     * @param sortType
     * @return list of all sorted transactions of a category type within set timeframe.
     */
    public List<Transaction> getTransactionsCategory(String category, ChartSort sortType) {
        if (!timeframeCategoryTransactions.containsKey(category)) {
            return new ArrayList<>(); // no null check needed in caller
        }

        List<Transaction> transactions = timeframeCategoryTransactions.get(category);

        if (sortType == ChartSort.DATE) {
            transactions.sort(Comparator.comparingLong(Transaction::getDateAsLong));
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

        Month curMonth = LocalDate.now().getMonth();
        for (Transaction transaction : transactions) {
            Month otherMonth = transaction.getDate().getMonth();

            // month comparison seems to work with ==, but I changed it to .equals to be safe.
            if (curMonth.equals(otherMonth)) {
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

            long daysBetween = ChronoUnit.DAYS.between(otherDate, curDate);
            if (daysBetween >= 0 && daysBetween <= days) {
                inTimeframe.add(transaction);
            }
        }

        return inTimeframe;
    }

    /**
     * Returns the list of all transactions by category type made between a start and an end date
     * @param category
     * @param start
     * @param end
     * @return list of all transactions by category type made between two dates.
     */
    private List<Transaction> getTransactionsBetween(String category, LocalDate start, LocalDate end) {
        List<Transaction> transactions = allCategoryTransactions.get(category);
        List<Transaction> inTimeframe = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate otherDate = transaction.getDate();

            if (otherDate.isEqual(start) || otherDate.isEqual(end) ||
                    (otherDate.isAfter(start) && otherDate.isBefore(end))) {
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
     * Returns the net sum of transactions within set timeframe.
     * @return net sum of transactions within set timeframe.
     */
    public double getNet() {
        return timeframeSumTotal;
    }



    /**
     * Returns the set of categories of the transactions within set timeframe.
     *
     @return set of categories.
     */
    public Set<String> getCategorySet() {
        return timeframeCategoryTransactions.keySet();
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
    public void updateTimeframe(String timeframe) {
        if (timeframe.equals("All time")) {
            timeframeTransactions = allTransactions;
            timeframeCategoryTransactions = allCategoryTransactions;
            updateSums();
            return;
        }

        timeframeTransactions = new ArrayList<>();
        timeframeCategoryTransactions = new HashMap<>();

        for (String category : allCategoryTransactions.keySet()) {
            List<Transaction> inTimeframe = null;

            if (timeframe.equals("Past month")) {
                inTimeframe = getTransactionsPastDays(category, 30);
            } else if (timeframe.equals("Past week")) {
                inTimeframe = getTransactionsPastDays(category, 7);
            } else if (timeframe.equals("This month")) {
                inTimeframe = getTransactionsCategoryThisMonth(category);
            }

            timeframeTransactions.addAll(inTimeframe);
            timeframeCategoryTransactions.put(category, inTimeframe); // inTimeframe will never be null.
        }

        timeframeSums = new HashMap<>();
        updateSums();
    }

    /**
     * Sets a restriction on transactions to be within a custom start and end date.
     *
     * @param startDate
     * @param endDate
     */
    public void updateTimeframe(LocalDate startDate, LocalDate endDate) {
        timeframeTransactions = new ArrayList<>();
        timeframeCategoryTransactions = new HashMap<>();

        // skip looping transactions if the start date comes after the end date.
        // we know that no transactions will be between.
        if (!startDate.isAfter(endDate)) {
            for (String category : allCategoryTransactions.keySet()) {
                List<Transaction> inTimeframe = getTransactionsBetween(category, startDate, endDate);

                timeframeTransactions.addAll(inTimeframe);
                timeframeCategoryTransactions.put(category, inTimeframe);
            }
        }

        timeframeSums = new HashMap<>();
        updateSums();
    }

    /**
     * Updates the sums of the categories within a timeframe.
     */
    private void updateSums() {
        timeframeSumTotal = 0.0;
        for (String category : timeframeCategoryTransactions.keySet()) {
            timeframeSumTotal += sumCategory(category);
        }
    }

    /**
     * Sets the sum for a category of transactions within a timeframe.
     * @param category
     */
    private double sumCategory(String category) {
        double sum = 0.0;

        for (Transaction transaction : timeframeCategoryTransactions.get(category)) {
            sum += transaction.getAmount();
        }

        timeframeSums.put(category, sum);
        return sum;
    }

    /**
     * Returns the date of the earliest transaction within the timeframe.
     *
     * ex:
     * timeframe between 11-20 and 11-30
     * earliest transaction 11-23
     *
     * returns 11-23 date
     *
     * @return the date of the earliest transaction within the timeframe.
     */
    public LocalDate getStartDate() {
        LocalDate start = null;

        for (Transaction transaction : timeframeTransactions) {
            LocalDate date = transaction.getDate();

            if (start == null || start.isAfter(date)) {
                start = date;
            }

        }

        return start;
    }

    /**
     * Returns the date of the latest transaction within the timeframe.
     *
     * ex:
     * timeframe between 11-20 and 11-30
     * latest transaction 11-27
     *
     * returns 11-27 date
     *
     * @return the date of the latest transaction within the timeframe.
     */
    public LocalDate getEndDate() {
        LocalDate end = null;

        for (Transaction transaction : timeframeTransactions) {
            LocalDate date = transaction.getDate();

            if (end == null || end.isBefore(date)) {
                end = date;
            }

        }

        return end;
    }
}
