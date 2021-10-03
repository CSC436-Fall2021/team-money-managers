package csc.arizona.moneymanager.TransactionUI;

import java.time.LocalDate;

/**
 * Models a transaction
 */
public class Transaction {

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
