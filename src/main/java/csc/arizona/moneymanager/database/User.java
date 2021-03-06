package csc.arizona.moneymanager.database;

import csc.arizona.moneymanager.MainUI.UserSetting;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private ObjectId id;



    private String username;
    private List<Transaction> transactions;
    private UserSetting settings;

    public UserSetting getSettings() {
        return settings;
    }

    public void setSettings(UserSetting settings) {
        this.settings = settings;
    }

    public User() {
        transactions = new ArrayList<>();
    }

    public User(String username) {
        this.username = username;
        this.transactions = new ArrayList<>();
        this.settings = new UserSetting();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }

    public void addTransactions(Transaction transaction){
        transactions.add(transaction);
    }

    public String getUsername() {
        return username;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
