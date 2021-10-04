package csc.arizona.moneymanager.TransactionUI;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.Month;

/**
 * Provides input functionality for creating transactions.
 *
 * Input for date of a transaction includes UI to pick a date.
 *
 *
 * Input for amount of transaction is a text field, with a dropbox to specify its category.
 *
 * This is displayed as a single row of graphical elements.
 *
 * A new transaction will be created if the user clicks on the enter button or
 * presses enter after inserting an amount. The amount field is cleared after entering.
 *
 */
public class TransactionUI extends BorderPane {

    DatePicker dateInput;

    ComboBox<Category> categoryDropDown;
    TextField amountInput;


    public TransactionUI() {
        HBox transactionInput = new HBox();

        // date input
        dateInput = new DatePicker();
        dateInput.setEditable(false); // must pick date from UI.

        // transaction amount and category input
        categoryDropDown = new ComboBox(FXCollections.observableArrayList(Category.values()));
        amountInput = new TextField();
        Button enterButton = new Button("Enter");

        amountInput.setPromptText("Amount");

        // disable custom input in dropboxes
        categoryDropDown.setEditable(false);

        amountInput.setOnAction(new EnterTransactionHandler());
        enterButton.setOnAction(new EnterTransactionHandler());

        // add all elements to HBox row
        transactionInput.getChildren().add(dateInput);
        transactionInput.getChildren().add(categoryDropDown);
        transactionInput.getChildren().add(amountInput);
        transactionInput.getChildren().add(enterButton);

        //transactionInput.setPadding(new Insets(20));
        transactionInput.setSpacing(5);
        setCenter(transactionInput);




    }

    /**
     * Creates a Transaction object using the information given by the user.
     */
    private class EnterTransactionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            LocalDate date = dateInput.getValue();
            Category category = categoryDropDown.getValue();

            /*
                Error handling for missing information
             */
            if (date == null) {
                System.out.println("Enter a date.");
                return;
            }

            if (category == null) {
                System.out.println("Pick a category.");
                return;
            }

            if (amountInput.getText().isEmpty()){
                System.out.println("Enter an amount.");
                return;
            }

            double amount = Double.parseDouble(amountInput.getText());

            Transaction toAdd = new Transaction(date, category, amount);

            System.out.println(toAdd.getDate());
            System.out.println(toAdd.getCategory());
            System.out.println(toAdd.getAmount());

            // reset input field for amount, but leave date and category as is.
            amountInput.clear();

        }
    }
}
