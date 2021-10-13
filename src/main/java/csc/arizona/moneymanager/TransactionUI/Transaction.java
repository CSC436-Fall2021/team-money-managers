package csc.arizona.moneymanager.TransactionUI;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Models a transaction.
 *
 * Stores the amount (positive or negative) of a transaction,
 * as well as a category describing it and the date of the transaction.
 */
public class Transaction implements Serializable {


    private LocalDate date;
    private double amount;
    private Category category;

    public Transaction(LocalDate date, Category category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}
