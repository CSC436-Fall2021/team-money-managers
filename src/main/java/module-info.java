module csc.arizona.moneymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires junit;
    requires org.mongodb.bson;
    requires jdk.jsobject;
    requires java.logging;

    opens csc.arizona.moneymanager.database;
    exports csc.arizona.moneymanager.database;
    opens csc.arizona.moneymanager.Login;
    exports csc.arizona.moneymanager.Login;
    opens csc.arizona.moneymanager.MainUI;
    exports csc.arizona.moneymanager.MainUI;
    opens csc.arizona.moneymanager.TransactionUI;
    exports csc.arizona.moneymanager.TransactionUI;
    opens csc.arizona.moneymanager.Charts;
    exports csc.arizona.moneymanager.Charts;

    opens csc.arizona.moneymanager;
    exports csc.arizona.moneymanager;
}
