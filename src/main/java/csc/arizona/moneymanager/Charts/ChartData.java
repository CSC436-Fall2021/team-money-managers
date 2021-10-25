package csc.arizona.moneymanager.Charts;

import csc.arizona.moneymanager.TransactionUI.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartData {

    Map<String, List<Transaction>> positiveBalances;
    Map<String, List<Transaction>> negativeBalances;

    public ChartData(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();

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


        }
    }

    public List<Transaction> getExpensesCategory(String category) {
        if (negativeBalances.containsKey(category)) {
            return negativeBalances.get(category);
        }

        return null;
    }

    public List<Transaction> getIncomesCategory(String category) {
        if (positiveBalances.containsKey(category)) {
            return positiveBalances.get(category);
        }

        return null;
    }
}
