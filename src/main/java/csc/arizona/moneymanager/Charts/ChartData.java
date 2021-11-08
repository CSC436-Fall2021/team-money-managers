package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;

import java.util.*;

/**
 * Provides useful functions for getting data about a list of Transactions
 *  for the purpose of displaying graphical information in a chart.
 */
public class ChartData {

    //private Set<String> categoryNames; // ease of access color coding. just a set of all transaction categories.
    // not needed because == categorySums.keySet().

    private Map<String, List<Transaction>> positiveBalances;
    private Map<String, List<Transaction>> negativeBalances;
    private Map<String, Double> categorySums;

    public ChartData(List<Transaction> transactions) {
        //categoryNames = new HashSet<String>();
        positiveBalances = new HashMap<>();
        negativeBalances = new HashMap<>();
        categorySums = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();

            //categoryNames.add(category);

            if (transaction.isIncome()) {
                if (!positiveBalances.containsKey(category)) {
                    positiveBalances.put(category, new ArrayList<Transaction>());
                }

                positiveBalances.get(category).add(transaction);
            } else {
                if (!negativeBalances.containsKey(category)) {
                    negativeBalances.put(category, new ArrayList<Transaction>());
                }

                negativeBalances.get(category).add(transaction);
            }

            if (!categorySums.containsKey(category)) {
                categorySums.put(category, transaction.getAmount());
            } else {
                double prevAmount = categorySums.get(category);
                categorySums.put(category, prevAmount + transaction.getAmount());
            }

        }
    }

    /**
     * Returns the list of all negative balances of a category's spending.
     * @param category
     * @return list of all negative balances of a category's spending.
     */
    public List<Transaction> getExpensesCategory(String category) {
        if (negativeBalances.containsKey(category)) {
            return negativeBalances.get(category);
        }

        return new ArrayList<Transaction>(); // no null check needed in caller
    }

    /**
     * Returns the gross expense (all negative balances) of a category's spending.
     * @param category
     * @return sum of all negative balances of a category's spending.
     */
    public double getGrossExpenseCategory(String category) {
        double sum = 0.0;

        for (Transaction transaction : negativeBalances.get(category)) {
            sum += transaction.getAmount();
        }
        return sum;
    }

    /**
     * Returns the list of all positive balances of a category's spending.
     * @param category
     * @return list of all positive balances of a category's spending.
     */
    public List<Transaction> getIncomesCategory(String category) {
        if (positiveBalances.containsKey(category)) {
            return positiveBalances.get(category);
        }

        return new ArrayList<Transaction>(); // no null check needed in caller
    }

    /**
     * Returns the gross income (all positive balances) of a category's spending.
     * @param category
     * @return sum of all positive balances of a category's spending.
     */
    public double getGrossIncomeCategory(String category) {
        double sum = 0.0;

        for (Transaction transaction : positiveBalances.get(category)) {
            sum += transaction.getAmount();
        }
        return sum;
    }

    /**
     * Returns the net sum of incomes and expenses of a category's spending.
     * @param category
     * @return net sum of category's spending.
     */
    public double getNetCategory(String category) {
        return categorySums.get(category);
    }

    /**
        Returns the set of categories of the transactions the ChartData was constructed with.
     @return set of categories.
     */
    public Set<String> getCategorySet() {
        return categorySums.keySet();
    }
}
