package csc.arizona.moneymanager.database.test;
//

import csc.arizona.moneymanager.MainUI.UserSetting;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import csc.arizona.moneymanager.database.DatabaseHandler;
import csc.arizona.moneymanager.database.User;
import javafx.scene.control.PasswordField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandlerTest {

    public static void main(String[] args){
//        DatabaseHandler.turnLoggerOff();
        DatabaseHandler handler = new DatabaseHandler();
        runTests(handler);
    }

    private static void runTests(DatabaseHandler handler){
        if (connectTest(handler)){
            addingAndGettingCredentialsTest(handler);

            //deleteUserTest(handler);
        }
    }

    private static void addingAndGettingCredentialsTest(DatabaseHandler handler) {
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
            transactionList.add(new Transaction(LocalDate.now(), "rent", 710, "December"));
            transactionList.add(new Transaction(LocalDate.now(), "food", 30.2, "Pasta"));
            transactionList.add(new Transaction(LocalDate.now().minusDays(30), "stock", 100, "Bitcoin"));
            transactionList.add(new Transaction(LocalDate.now().plusDays(2), "stock", 200, "Ethereum"));
            transactionList.add(new Transaction(LocalDate.now().minusDays(10), "gas", 48.23, "Full Tank"));
            transactionList.add(new Transaction(LocalDate.now().minusDays(1), "gas", 19.92, "Half Tank"));
            User test = new User();
            test.setUsername("test");
            test.setTransactions(transactionList);
            UserSetting settings = new UserSetting();
            settings.setBudget(2000);
            settings.setBudgetDuration("Monthly");
            settings.addCategoryName("test");
            test.setSettings(settings);
            if (handler.updateUserData(test,true)){
                System.out.println("addTransactions(): passed");
            } else {
                System.out.println("addTransactions(): failed");
            }
            if (handler.removeTransaction(test, new Transaction(LocalDate.now(), "food", 30.2, "Pasta"), true)){
                System.out.println("removeTransaction(): passed");
            } else {
                System.out.println("removeTransaction(): failed");
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