package csc.arizona.moneymanager.TransactionUI;

import javafx.beans.property.SimpleStringProperty;

public class TableTransaction {
    public SimpleStringProperty date =new SimpleStringProperty();;
    public SimpleStringProperty amount = new SimpleStringProperty();;
    public SimpleStringProperty categeroy = new SimpleStringProperty();;

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


    public TableTransaction(String date, String categeroy, String amount) {
        this.date.set(date);
        this.amount.set(amount);
        this.categeroy.set(categeroy);
    }



}
