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

    // public so UI and (if wanted) database can know max_length. static and final should be fine.
    public static final int MEMO_MAX_LENGTH = 16; // where to cut memo off if too long input.

    private LocalDate date;
    private double amount;
    private String category;
    private String memo;

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.amount, amount) == 0 && date.equals(that.date) && category.equals(that.category) && memo.equals(that.memo);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public Transaction(){
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
        this.memo = "";
    }

    public Transaction(LocalDate date, String category, double amount, String memo) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.memo = memo.substring(0, Math.min(memo.length(), MEMO_MAX_LENGTH));
        // memo length limited in TransactionUI's input field
        //  but substring still useful if Transaction ever created elsewhere (maybe for debug reasons?)
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

    public long getDateAsLong() {
        //long val = date.getLong(ChronoField.EPOCH_DAY);
        long val = date.toEpochDay();
        return val;
    }

    public String getMemo() { return memo; }


    //public static int getMemoMaxLength() { return MEMO_MAX_LENGTH; }
}
