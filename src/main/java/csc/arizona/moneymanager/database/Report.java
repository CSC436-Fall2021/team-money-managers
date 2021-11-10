package csc.arizona.moneymanager.database;

import csc.arizona.moneymanager.TransactionUI.Transaction;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Report {

    private Date start, end;
    private List<Transaction> transactions;

    public Report(List<Transaction> transactions){
        this.transactions = transactions;
    }

    public void setTimeframe(Date start, Date end){
        this.start = start;
        this.end = end;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }


}
