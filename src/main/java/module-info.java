module csc.arizona.moneymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires junit;
    requires org.mongodb.bson;
    requires com.google.gson;
    requires jdk.jsobject;


    opens csc.arizona.moneymanager to javafx.fxml;
    exports csc.arizona.moneymanager.database;
    opens csc.arizona.moneymanager.database to javafx.fxml;
}