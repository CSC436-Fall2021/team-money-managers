package csc.arizona.moneymanager.TransactionUI;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

/**
 * Models a transaction.
 *
 * Stores the amount (positive or negative) of a transaction,
 * as well as a category describing it and the date of the transaction.
 */
public class Transaction implements Serializable {


    private LocalDate date;
    private double amount;
    private String category;

    public Transaction() {

    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Transaction(LocalDate date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public int getDateAsInt() {
        int val = date.get(ChronoField.EPOCH_DAY);
        return val;
    }

    public static LocalDate getDateFromInt(int val) {
        LocalDate date = LocalDate.ofEpochDay(val);
        return date;
    }
}
