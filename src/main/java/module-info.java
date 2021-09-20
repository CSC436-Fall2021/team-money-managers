module csc.arizona.moneymanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens csc.arizona.moneymanager to javafx.fxml;
    exports csc.arizona.moneymanager;
}