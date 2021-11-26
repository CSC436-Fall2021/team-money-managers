package csc.arizona.moneymanager.TransactionUI;
//
import javafx.beans.property.SimpleStringProperty;

public class TableTransaction {
    public SimpleStringProperty date =new SimpleStringProperty();
    public SimpleStringProperty amount = new SimpleStringProperty();
    public SimpleStringProperty categeroy = new SimpleStringProperty();
    public SimpleStringProperty memo = new SimpleStringProperty();

    public String getMemo() {
        return memo.get();
    }

    public SimpleStringProperty memoProperty() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo.set(memo);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getAmount() {
        return amount.get();
    }

    public SimpleStringProperty amountProperty() {
        return amount;
    }

    public String getCategeroy() {
        return categeroy.get();
    }

    public SimpleStringProperty categeroyProperty() {
        return categeroy;
    }


    public TableTransaction(String date, String categeroy, String amount, String memo) {
        this.date.set(date);
        this.amount.set(amount);
        this.categeroy.set(categeroy);
        this.memo.set(memo);
    }



}
