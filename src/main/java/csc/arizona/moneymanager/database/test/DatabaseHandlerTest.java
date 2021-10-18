package csc.arizona.moneymanager.database.test;


import csc.arizona.moneymanager.TransactionUI.Category;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import csc.arizona.moneymanager.database.DatabaseHandler;
import csc.arizona.moneymanager.database.User;
import javafx.scene.control.PasswordField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandlerTest {

    public static void main(String[] args){
        DatabaseHandler.turnLoggerOff();
        DatabaseHandler handler = new DatabaseHandler();
        runTests(handler);
    }

    private static void runTests(DatabaseHandler handler){
        if (connectTest(handler)){
            addingAndGettingCredentialsTest(handler);

            deleteUserTest(handler);
        }
    }

    private static void addingAndGettingCredentialsTest(DatabaseHandler handler){
        if (handler.addUser("test", "test")){
            System.out.println("addUser(): passed");
            if(handler.userExists("test")){
                System.out.println("userExists(): passed");
            } else {
                System.err.println("userExists(): failed");
            }
            if(handler.validateUser("test","test")){
                System.out.println("validateUser(): passed");
            } else{
                System.err.println("validateUser(): failed");
            }
            List<Transaction> transactionList = new ArrayList<>();
            for (int i = 0; i < 5; i++){
                Transaction temp = new Transaction(LocalDate.now().plusDays(i), Category.OTHER, i);
                transactionList.add(temp);
            }
            User test = new User("mason");
            test.setTransactions(transactionList);
            if (handler.updateUserData(test,true)){
                System.out.println("addTransactions(): passed");
            } else {
                System.out.println("addTransactions(): failed");
            }

        } else{
            System.err.println("addUser(): failed");
        }
    }

    private static void deleteUserTest(DatabaseHandler handler){
        if (handler.deleteUser("test", "test")){
            System.out.println("deleteUser(): passed");
        } else{
            System.err.println("deleteUser(): failed");
        }
    }

    private static boolean connectTest(DatabaseHandler handler){
        if (!handler.connectToDatabase()){
            System.err.println("connectToDatabase(): failed");
            return false;
        } else{
            System.out.println("connectToDatabase(): passed");
            return true;
        }

    }
}