package csc.arizona.moneymanager.database;

import csc.arizona.moneymanager.TransactionUI.Transaction;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String username;
    private List<Transaction> transactions;


    public User(String username, List<Transaction> transactions) {
        this.username = username;
        this.transactions = transactions;
    }

    public String getUsername() {
        return username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
