package csc.arizona.moneymanager.TransactionUI;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.Month;

/**
 * Provides input functionality for creating transactions.
 *
 * Input for date of a transaction includes dropdown boxes for the month, day,
 * and an input text field for the year.
 *
 *
 * Input for amount of transaction is a text field, with a dropbox to specify its category.
 *
 * This is displayed as a single row of graphical elements.
 *
 * A new transaction will be created if the user clicks on the enter button or
 * presses enter after inserting an amount.
 *
 */
public class TransactionUI extends BorderPane {

    ComboBox<Month> monthDropDown;
    ComboBox<Integer> dayDropDown;
    TextField yearInput;
    Integer days[];

    ComboBox<Category> categoryDropDown;
    TextField amountInput;


    public TransactionUI() {
        HBox transactionInput = new HBox();

        days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i+1;
        }

        // date input
        monthDropDown = new ComboBox<>(FXCollections.observableArrayList(Month.values()));
        dayDropDown = new ComboBox<>(FXCollections.observableArrayList(days));
        yearInput = new TextField();

        // transaction amount and category input
        categoryDropDown = new ComboBox(FXCollections.observableArrayList(Category.values()));
        amountInput = new TextField();
        Button enterButton = new Button("Enter");

        yearInput.setPromptText("Year");
        amountInput.setPromptText("Amount");

        //categoryDropDown.setValue(Category.UNDEFINED);

        // disable custom input in dropboxes
        monthDropDown.setEditable(false);
        dayDropDown.setEditable(false);
        categoryDropDown.setEditable(false);

        amountInput.setOnAction(new EnterTransactionHandler());
        enterButton.setOnAction(new EnterTransactionHandler());

        // add all elements to HBox row
        transactionInput.getChildren().add(monthDropDown);
        transactionInput.getChildren().add(dayDropDown);
        transactionInput.getChildren().add(yearInput);
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

            Month month = monthDropDown.getValue();
            int day = dayDropDown.getValue();
            int year = Integer.parseInt(yearInput.getText());

            LocalDate date = LocalDate.of(year, month, day);


            Category category = categoryDropDown.getValue();
            double amount = Double.parseDouble(amountInput.getText());

            Transaction toAdd = new Transaction(date, category, amount);

            System.out.println(toAdd.getDate());
            System.out.println(toAdd.getCategory());
            System.out.println(toAdd.getAmount());

            amountInput.clear();

        }
    }
}
