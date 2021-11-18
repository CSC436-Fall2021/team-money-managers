package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;

import java.util.*;

/**
 * Provides useful functions for getting data about a list of Transactions
 *  for the purpose of displaying graphical information in a chart.
 */
public class ChartData {

    private Map<String, List<Transaction>> categoryTransactions;
    private Map<String, Double> categorySums;

    public ChartData(List<Transaction> transactions) {

        categoryTransactions = new HashMap<>();
        categorySums = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();


            if (!categoryTransactions.containsKey(category)) {
                categoryTransactions.put(category, new ArrayList<Transaction>());
            }

            categoryTransactions.get(category).add(transaction);


            if (!categorySums.containsKey(category)) {
                categorySums.put(category, transaction.getAmount());
            } else {
                double prevAmount = categorySums.get(category);
                categorySums.put(category, prevAmount + transaction.getAmount());
            }

        }
    }

    /**
     * Returns the list of all transactions by category type.
     * @param category
     * @return list of all transactions by category type.
     */
    public List<Transaction> getTransactionsCategory(String category) {
        if (categoryTransactions.containsKey(category)) {
            return categoryTransactions.get(category);
        }

        return new ArrayList<Transaction>(); // no null check needed in caller
    }

    /**
     * Returns the net sum of transactions of a category type.
     * @param category
     * @return net sum of transactions of a category type.
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
